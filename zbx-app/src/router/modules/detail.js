import _import from '../_import'
export default {
  path: '/detail/:id/:biztype',
  meta: { title: '业主页', auth: ['owner', 'recyler', 'admin'] },
  component: _import('detail')
}
