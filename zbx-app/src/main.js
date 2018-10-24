// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.less'
// import * as components from './components'
import Vant from 'vant'
import 'vant/lib/vant-css/index.css'

import request from '@/api'
// lib-flexible + rem
import 'lib-flexible'

Vue.config.productionTip = false
// Vue.use(ElementUI, { size: 'medium' })
Vue.use(Vant)
// const convertName = (name) =>
//   name.replace(/[A-Z]/g, m => `-${m.toLowerCase()}`).replace(/^-/, '')
// Object.entries(components).forEach(
//   ([key, cmp]) => Vue.component(cmp.name || convertName(key), cmp)
// )
Vue.use(request)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
