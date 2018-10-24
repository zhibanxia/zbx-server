import _import from '../_import'
export default {
  path: '/recyler',
  meta: { title: '回收', auth: ['recyler'] },
  children: [
    {
      path: '/recyler',
      meta: { title: '回收', auth: ['recyler'] },
      component: _import('recyler')
    },
    {
      path: 'detail',
      meta: { title: '回收', auth: ['recyler'] },
      component: _import('recylerDetail')
    }
  ]
}
