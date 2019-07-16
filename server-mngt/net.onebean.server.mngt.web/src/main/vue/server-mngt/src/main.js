import Vue from 'vue'
import Layout from './components/layout/Layout.vue'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import './assets/css/bin/bin.css'
import utils from './assets/js/utils/utils.js'
import apiPath from './assets/js/constant/ApiPath'
import routerConfig from './assets/js/router/routerConfig'
import vuexConfig from './assets/js/vuex/vuexConfig'



Vue.use(iView)

Vue.config.productionTip = false
utils.vue = this
Vue.prototype.utils = utils
Vue.prototype.API_PTAH = apiPath


const router = routerConfig.initRouterGenerate
const vuex = vuexConfig.initsStoreInstance

router.beforeEach((to, from, next) => {
  if (to.path != '/err' && Object.keys(vuex.state.uagCurrentLoginUserInfo).length <= 0) {
    // next('/err')
  }
  next()
})

new Vue({
  router: router,
  store: vuex,
  render: h => h(Layout)
}).$mount('#app')
