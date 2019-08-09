import apiPath from '../constant/ApiPath'
import utils from '../utils//utils'
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)


let routerConfig = {}

routerConfig.routerPath = [
  {
    path: '',
    redirect: '/0'
  }
]


routerConfig.initRouterGenerate = new VueRouter({
  routes: []
})


routerConfig.remoteRouterGenerate = async function(router,store) {
  const isLoad =  store.state.isLoadMenu
  if(!isLoad){
    const menuArr = await utils.netUtil.postAsync(apiPath.getMenuList, {})
    let resArr = []
    routerConfig.routerPath.forEach(item => {
      resArr.push(item)
    })
    menuArr.forEach(item => {
      item.component = () => import('@/components/appFrame/AppFrame')
      resArr.push(item)
    })
    localStorage.menuArr = JSON.stringify(resArr)
    router.addRoutes(resArr)
    store.commit('doLoadingMenu')
  }

}



export default routerConfig
