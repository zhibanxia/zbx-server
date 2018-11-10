// 基于axios封装的请求
import axios from 'axios'
// import { Message } from 'element-ui'
import { Dialog } from 'vant'
import { getToken } from '@/utils/auth'
import { hitCache, formatRequestData } from './requestUtil'

const IS_DEV_ENV = process.env.NODE_ENV === 'development'
// 本地开发时使用的mock数据来源
// ‘local’ 使用本地的mock数据
// ‘dev’ 使用开发环境的数据
// ‘ams’ 使用ams 接口文档系统中的mock数据
const MOCK_TYPE = 'ams'
let ROOT = ''

if (IS_DEV_ENV) {
  ROOT = {
    'local': '/mock',
    'dev': '/dev',
    'ams': '/ams'
  }[MOCK_TYPE] || ROOT
}

const cacheData = {}

const request = axios.create({
  baseURL: ROOT,
  timeout: 30000
})

// const csrfToken = getToken()

let loginTimer = null

// request部分
request.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json'
  // config.headers['csrfToken'] = csrfToken
  return config
}, error => Promise.reject(error))

// response部分
request.interceptors.response.use(res => {
  const data = res.data
  if (!Array.isArray(data) && !data.success) {
    Dialog.alert({message: data.message || data.desc || '请求错误'})
  }
  return data
}, error => {
  Dialog.alert(error.message)
  return Promise.reject(error)
})

function ajax(method, url = '', data, config = {}) {
  return new Promise((resolve, reject) => {
    if (hitCache({ ts: config.cache, dataInCache: cacheData[url], params: data })) {
      return resolve(cacheData[url])
    }
    if (config.dataType === 'params') {
      if (MOCK_TYPE === 'dev' || !IS_DEV_ENV) {
        url = Object.values(data).reduce((prev, v) => `${prev}/${v}`, url)
      } else if (MOCK_TYPE === 'ams') {
        url = Object.keys(data).reduce((prev, v) => `${prev}/\$\{${v}\}`, url)
      }
    }

    request[method](url,
      formatRequestData(data, method, config), config.axiosConfig
    ).then(res => {
      if (res.notLogin) {
        Dialog.alert(IS_DEV_ENV ? '登陆过期，请更新cookie' : '登陆过期，正在跳转至登陆页面')
        if (IS_DEV_ENV) return // 本地开发不做未登录跳转
        loginTimer && clearTimeout(loginTimer)
        loginTimer = setTimeout(() => {
          const host = location.host.indexOf('mng') > -1 ? location.host : 'mng.duibadev.com.cn'
          const redirectUrl = location.href.replace(/#(\S+)$/, '?urltmp=$1')
          location.href = `//${host.replace('mng', 'sso')}/login?redirect=${encodeURIComponent(redirectUrl)}&systemId=1`
        }, 1500)
        return
      }

      if (config.hideErrorMsg) {
        return resolve(res)
      }

      const msg = res.message || res.desc
      if (res.success) {
        config.showSuccessMsg && Dialog.alert({message: msg || '操作成功'})
        if (config.cache) {
          cacheData[url] = {
            data: res.data,
            ts: Date.now(),
            params: data,
            isCache: true
          }
        }
        resolve(res)
      } else {
        Dialog.alert({message: msg || '请求错误'})
        reject()
      }
    }).catch(reject)
  })
}

export default ajax
