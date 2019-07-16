import AppInfoList from '../../../components/appInfo/AppInfoList.vue'
import AppInfoAdd from '../../../components/appInfo/AppInfoAdd.vue'
import AppInfoEditor from '../../../components/appInfo/AppInfoEditor.vue'
import ErrorCard from '../../../components/error/ErrorCard'
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

let routerConfig = {}

routerConfig.routerPath = [
  {
    path: '/app-info-list',
    component: AppInfoList,
    icon: 'ios-appstore',
    name: '应用列表'
  },
  {
    path: '/app-info-list/app-info-add',
    component: AppInfoAdd,
    icon: 'md-add-circle',
    name: '添加应用'
  },
  {
    path: '/app-info-list/app-info-editor/:id',
    component: AppInfoEditor,
    icon: 'ios-brush',
    name: '编辑应用'
  },
  {
    path: '/err',
    component: ErrorCard
  },
  {
    path: '',
    redirect: '/app-info-list'
  }
]

routerConfig.initRouterGenerate = new VueRouter({
  routes: routerConfig.routerPath
})

export default routerConfig
