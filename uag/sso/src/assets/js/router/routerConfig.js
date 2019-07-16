import SmsCodeLoginCard from '../../../components/loginCard/SmsCodeLoginCard'
import PasswordLoginCard from '../../../components/loginCard/PasswordLoginCard'
import ErrorCard from '../../../components/loginCard/ErrorCard'
import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

let routerConfig = {}


routerConfig.routerPath = [
  { 
    path: '/sms',
    component: SmsCodeLoginCard,
    icon: 'ios-appstore',
    name: '短信验证码登录'
  },
  { 
    path: '/password',
    component: PasswordLoginCard,
    icon: 'ios-appstore',
    name: '账号密码登录'
  },
  { 
    path: '/err',
    component: ErrorCard,
    icon: 'ios-appstore',
    name: '错误页面'
  },
  {
    path: '',
    redirect: '/sms'
  }
]


routerConfig.initRouterGenerate = new VueRouter({
  routes: routerConfig.routerPath
})



export default routerConfig
