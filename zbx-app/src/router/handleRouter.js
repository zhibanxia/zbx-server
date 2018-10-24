import Layout from '@/views/layout'
import EssentialContainer from '@/views/layout/EssentialContainer'
// import HorzMenuContainer from '@/views/layout/HorzMenuContainer'
import _import from './_import'

export function filterRouter (routerMap, authMap) {
  return routerMap.filter(v => {
    if (v.children) {
      v.children = filterRouter(v.children, authMap)
    }
    return !v.meta.auth || !v.meta.auth.length || v.meta.auth.some(w => authMap[w])
  })
}

export function completeRouter (router, subRouter) {
  if (!router) return
  let component = router.component || Layout
  if (!router.component && subRouter) {
    component = router.meta && EssentialContainer
  }
  if (typeof component === 'string') {
    component = _import(component)
  }

  return {
    meta: {},
    ...router,
    component,
    children: router.children && router.children.map(v => completeRouter(v, true))
  }
}
