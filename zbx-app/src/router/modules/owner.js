import _import from '../_import'
export default {
  path: '/owner',
  meta: { title: '业主页', auth: ['owner'] },
  // component: _import('owner'),
  children: [
    {
      path: '/',
      meta: { title: '业主页', auth: ['owner'] },
      component: _import('owner/owner')
    },
    {
      path: 'publish',
      meta: { title: '发布回收', auth: ['owner'] },
      component: _import('owner/publish')
    },
    {
      path: 'publish/:id',
      meta: { title: '更新回收', auth: ['owner'] },
      component: _import('owner/publish')
    },
    {
      path: 'detail',
      meta: { title: '回收', auth: ['owner'] },
      component: _import('owner/ownerDetail')
    }
  ]
}
