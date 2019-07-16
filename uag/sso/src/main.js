import Vue from 'vue'
import Layout from './components/layout/Layout.vue'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import './assets/css/bin/bin.css'
import utils from './assets/js/utils/utils.js'
import apiPath from './assets/js/constant/ApiPath'
import routerConfig from './assets/js/router/routerConfig'
import vuexConfig from './assets/js/vuex/vuexConfig'
import VueCookies from 'vue-cookies'


Vue.use(iView)
Vue.use(VueCookies)

Vue.config.productionTip = false
utils.vue = this
Vue.prototype.utils = utils
Vue.prototype.API_PTAH = apiPath


const router = routerConfig.initRouterGenerate
const vuex = vuexConfig.initsStoreInstance

new Vue({
  router: router,
  store: vuex,
  render: h => h(Layout)
}).$mount('#app')
