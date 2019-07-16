import UagUserInfoList from '../../../components/uagUserInfo/UagUserInfoList'
import UagUserInfoAddAccount from '../../../components/uagUserInfo/UagUserInfoAddAccount'
import UagUserInfoEditorAccount from '../../../components/uagUserInfo/UagUserInfoEditorAccount'
import uagLogList from '../../../components/uagLog/uagLogList'
import ErrorCard from '../../../components/error/ErrorCard'
import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

let routerConfig = {}


routerConfig.routerPath = [
  { 
    path: '/uag-log-list',
    component: uagLogList,
    icon: 'md-list-box',
    name: '操作日志'
  },
  { 
    path: '/uag-userinfo-list',
    component: UagUserInfoList,
    icon: 'md-contact',
    name: '用户管理'
  },
  { 
    path: '/uag-userinfo-list/uag-userinfo-add/:appId',
    component: UagUserInfoAddAccount,
    icon: 'md-add-circle',
    name: '添加用户'
  },
  { 
    path: '/uag-userinfo-list/uag-userinfo-editor/:appId/:userId',
    component: UagUserInfoEditorAccount,
    icon: 'ios-brush',
    name: '编辑用户'
  },
  {
    path: '/err',
    component: ErrorCard
  },
  {
    path: '',
    redirect: '/uag-userinfo-list'
  }
]


routerConfig.initRouterGenerate = new VueRouter({
  routes: routerConfig.routerPath
})



export default routerConfig
