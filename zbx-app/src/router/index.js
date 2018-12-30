import Vue from 'vue'
import Router from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import _import from './_import'
import routeRequire from './routeRequire'
import { filterRouter, completeRouter } from './handleRouter'
import moduleRouters from './modules'

Vue.use(Router)
NProgress.configure({ showSpinner: false })
// 路由
const initRouterMap = [
  {
    path: '/',
    meta: { title: '首页', icon: 'home' },
    component: _import('home/index')
  },
  {
    path: '/register',
    meta: { title: '注册' },
    component: _import('register/info')
  },
  {
    path: '/error',
    meta: { title: '审核' },
    component: _import('error')
  },
  {
    path: '/usercenter/recylerarea',
    meta: { title: '个人中心' },
    component: _import('usercenter/recylerArea')
  }
]

const asyncRouter = moduleRouters.map(v => completeRouter(v))
export const routerMap = {
  constantRouter: initRouterMap.map(v => completeRouter(v)),
  asyncRouter: []
}
const router = new Router({
  routes: routerMap.constantRouter
})

let hasCreateAsyncRouter = false
router.beforeEach(async (to, from, next) => {
  NProgress.start()
  // 获取权限列表生成动态路由
  if (!['/logout'].includes(to.path) && !hasCreateAsyncRouter) {
    hasCreateAsyncRouter = true
    // 进入首页，重新请求用户类型
    let iscache = false
    // if (to.path === '/') {
    //   iscache = false
    // }
    const { authMap, status, type } = await routeRequire.requireAuth(iscache)
    // 未注册，进入注册页面
    if (to.path.indexOf('/register') !== -1) {
      return next('/register')
    }
    // 用户状态不正常时
    if (status !== 1) {
      return next('/error?status=' + status + '&type=' + type)
    }

    // 从请求获取权限列表时生成路由
    if (authMap) {
      routerMap.asyncRouter = filterRouter(asyncRouter, authMap)
      router.addRoutes(routerMap.asyncRouter)
      console.log(routerMap.asyncRouter)
      if (to.path === '/') {
        let auth = Object.keys(authMap).filter(item => authMap[item])
        if (auth.length) {
          return next('/' + auth[0])
        }
      }
      return next(to)
    }
  }
  // 未匹配到到路径则跳转至首页
  if (to.matched.length === 0) {
    NProgress.done()
    return next('/error') // 如果未匹配到路由
  }
  async function handleMeta (meta) {
    try {
      for (let [key, value] of Object.entries(meta)) {
        value && routeRequire[key] && await routeRequire[key](next, value)
      }
    } finally {
      await next()
      NProgress.done()
    }
  }

  handleMeta(to.meta)
})

export default router
