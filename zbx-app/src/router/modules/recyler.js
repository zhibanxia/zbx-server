import _import from '../_import'
export default {
  path: '/recyler',
  meta: { title: '回收', auth: ['recyler'] },
  children: [
    {
      path: '/recyler',
      meta: { title: '回收', auth: ['recyler'] },
      component: _import('recyler/recyler')
    },
    {
      path: 'detail',
      meta: { title: '回收', auth: ['recyler'] },
      component: _import('recyler/recylerDetail')
    }
  ]
}
