import TenantInfoList from '../../../components/tenantInfo/TenantInfoList'
import TenantInfoAdd from '../../../components/tenantInfo/TenantInfoAdd'
import TenantInfoEditor from '../../../components/tenantInfo/TenantInfoEditor'
import ProvinceInfoList from '../../../components/provinceInfo/ProvinceInfoList'
import ProvinceInfoAdd from '../../../components/provinceInfo/ProvinceInfoAdd'
import ProvinceInfoEditor from '../../../components/provinceInfo/ProvinceInfoEditor'
import ErrorCard from '../../../components/error/ErrorCard'

import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

let routerConfig = {}

routerConfig.routerPath = [
  {
    path: '/tenant-info-list',
    component: TenantInfoList,
    icon: 'md-man',
    name: '租户列表'
  },
  {
    path: '/tenant-info-list/tenant-info-add',
    component: TenantInfoAdd,
    icon: 'ios-add-circle',
    name: '添加租户'
  },
  {
    path: '/tenant-info-list/tenant-info-editor/:id',
    component: TenantInfoEditor,
    icon: 'ios-brush',
    name: '编辑租户'
  },
  {
    path: '/province-info-list',
    component: ProvinceInfoList,
    icon: 'md-boat',
    name: '省份列表'
  },
  {
    path: '/province-info-list/province-info-add',
    component: ProvinceInfoAdd,
    icon: 'ios-add-circle',
    name: '新建省份'
  },
  {
    path: '/province-info-list/province-info-editor/:id',
    component: ProvinceInfoEditor,
    icon: 'ios-brush',
    name: '编辑省份'
  },
  {
    path: '/err',
    component: ErrorCard
  },
  {
    path: '',
    redirect: '/tenant-info-list'
  }
]

routerConfig.initRouterGenerate = new VueRouter({
  routes: routerConfig.routerPath
})

export default routerConfig
