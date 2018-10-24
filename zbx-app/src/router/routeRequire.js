import request from '@/api'
const globalData = {}

export default {
  get (key) {
    return globalData[key]
  },
  set (key, value) {
    globalData[key] = value
  },
  requireAuth (iscache) {
    return new Promise(resolve => {
      // let type = +getCookie('type') // 1: 业主 2: 回收人员 3: 管理员
      this.requireUserInfo(iscache).then(userInfo => {
        let type = userInfo.userType
        let authMap = null
        if (type) {
          authMap = {
            recyler: type === 2,
            owner: type === 1,
            admin: type === 3
          }
        }
        let status = userInfo.userStatus
        resolve({ authMap: authMap, status: status, type: type })
      })
    })
  },
  requireUserInfo (iscache) {
    return new Promise(resolve => {
      if (iscache) {
        const userInfo = this.get('userInfo')
        if (userInfo) {
          resolve(userInfo)
        }
      }
      request.get('getUserType')
        .then(res => {
          this.set('userInfo', res.data)
          resolve(this.get('userInfo'))
        })
    })
  },
  requireChannelAdmin (next) {
    return new Promise(resolve => {
      this.requireUserInfo().then((userInfo) => {
        if (userInfo.applicationManage) {
          resolve()
        } else {
          next('/application/traceList')
        }
      })
    })
  }
}
