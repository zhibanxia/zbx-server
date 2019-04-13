import _import from '../_import'
export default {
  path: '/admin',
  meta: { title: '管理页', auth: ['admin'] },
  children: [
    {
      path: '/',
      meta: { title: '首页', auth: ['admin'] },
      component: _import('admin/index')
    },
    {
      path: 'recyledetail/:id',
      meta: { title: '回收详情页', auth: ['admin'] },
      component: _import('admin/recyleDetail')
    }, {
      path: 'userdetail/:id/:userType/:userStatus',
      meta: { title: '用户详情页', auth: ['admin'] },
      component: _import('admin/userDetail')
    },
    {
      path: 'addservice',
      meta: { title: '添加服务类型', auth: ['admin'] },
      component: _import('admin/addServiceType')
    }
  ]
}
