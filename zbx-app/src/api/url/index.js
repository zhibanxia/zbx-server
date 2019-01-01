const urls = {
  get: {
    // 回收列表
    recyleList: '/rest/recycle/list',
    // 回收详情
    recyleDetail: '/rest/recycle/detail',
    // 获取用户类型
    getUserType: '/rest/user/getUserType',
    // 业主信息
    getYezhuUserInfo: '/rest/user/getYezhuUserInfo',
    // 管理员- 回收列表
    recylerListByAdmin: '/rest/recycle/list4Admin',
    // 管理员 - 获取回收人员信息
    getRecylerInfo: '/rest/user/getHuishouUserInfo',
    // 管理员 - 获取业主信息
    getOwnerInfo: '/rest/user/getYezhuUserInfo4Admin'
  },
  post: {
    // 图片上传
    upload: '/rest/upload/uploadImg',
    // 确认回收
    confirmRecycle: '/rest/recycle/confirmRecycle',
    // 业主删除信息
    delete: '/rest/recycle/delete',
    // 补充信息
    addUserDetail: '/rest/user/addUserDetail',
    // 回收完成
    completeRecycle: '/rest/recycle/completeRecycle',
    createRecyle: '/rest/recycle/create',
    updateRecyle: '/rest/recycle/update',
    // 管理员-审核回收
    verifyHuishou: '/rest/user/verifyHuishou',
    // 管理员 -修改用户状态
    modifyUserStatus: '/rest/user/modifyUserStatus',
    // 匹配小区信息
    searchByComplexName: '/rest/complex/searchByComplexName',
    // 管理员 -删除用户
    deleteUser: '/rest/user/deleteUser',
    // 管理员 -查询用户列表
    searchUser: '/rest/user/searchUser'
  },
  patch: {},
  delete: {}
}

const requireAll = requireContext => requireContext.keys().map(requireContext)
const req = require.context('./', false, /^\.\/(?!index).+\.js$/)
const modules = requireAll(req)

modules.forEach(({ default: urlModule }) =>
  Object.entries(urlModule).forEach(
    ([method, val]) => val && Object.assign(urls[method], val)
  )
)

export default urls
