import ServerInfoList from '../../../components/serverInfo/ServerInfoList.vue'
import ServerInfoAdd from '../../../components/serverInfo/ServerInfoAdd.vue'
import ServerInfoEditor from '../../../components/serverInfo/ServerInfoEditor.vue'
import ApiInfoList from '../../../components/apiInfo/ApiInfoList.vue'
import ApiInfoAdd from '../../../components/apiInfo/ApiInfoAdd.vue'
import ApiInfoEditor from '../../../components/apiInfo/ApiInfoEditor.vue'
import NginxNodeInfoList from '../../../components/nginxNodeInfo/NginxNodeInfoList'
import NginxNodeInfoAdd from '../../../components/nginxNodeInfo/NginxNodeInfoAdd'
import NginxNodeInfoEditor from '../../../components/nginxNodeInfo/NginxNodeInfoEditor'
import UpSteamNodeInfoList from '../../../components/upSteamNodeInfo/UpSteamNodeInfoList'
import UpSteamNodeInfoAdd from '../../../components/upSteamNodeInfo/UpSteamNodeInfoAdd'
import UpSteamNodeInfoEditor from '../../../components/upSteamNodeInfo/UpSteamNodeInfoEditor'
import ErrorCard from '../../../components/error/ErrorCard'

import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)


let routerConfig = {}


routerConfig.routerPath = [
  {
    path: '/upsteam-node-info-list',
    component: UpSteamNodeInfoList,
    icon: 'ios-leaf',
    name: '应用节点列表'
  },
  {
    path: '/upsteam-node-info-list/upsteam-node-info-add',
    component: UpSteamNodeInfoAdd,
    icon: 'md-add-circle',
    name: '添加应用节点'
  },
  {
    path: '/upsteam-node-info-list/upsteam-node-info-editor/:id',
    component: UpSteamNodeInfoEditor,
    icon: 'ios-brush',
    name: '编辑应用节点'
  },
  {
    path: '/nginx-node-info-list',
    component: NginxNodeInfoList,
    icon: 'md-git-compare',
    name: '网关节点列表'
  },
  {
    path: '/nginx-node-info-list/nginx-node-info-add',
    component: NginxNodeInfoAdd,
    icon: 'md-add-circle',
    name: '新增网关节点'
  },
  {
    path: '/nginx-node-info-list/nginx-node-info-editor/:id',
    component: NginxNodeInfoEditor,
    icon: 'ios-brush',
    name: '编辑网关节点'
  },
  {
    path: '/server-info-list',
    component: ServerInfoList,
    icon: 'ios-globe',
    name: '服务列表'
  },
  {
    path: '/server-info-list/api-info-list/:sid',
    component: ApiInfoList,
    icon: 'ios-grid',
    name: '接口管理'
  },
  {
    path: '/server-info-list/api-info-list/api-info-add/:sid',
    component: ApiInfoAdd,
    icon: 'md-add-circle',
    name: '添加接口'
  },
  {
    path: '/server-info-list/api-info-list/api-info-editor/:sid/:id',
    component: ApiInfoEditor,
    icon: 'ios-brush',
    name: '编辑接口'
  },
  {
    path: '/server-info-list/server-info-add',
    component: ServerInfoAdd,
    icon: 'md-add-circle',
    name: '添加服务'
  },
  {
    path: '/server-info-list/server-info-editor/:id',
    component: ServerInfoEditor,
    icon: 'ios-brush',
    name: '编辑服务'
  },
  {
    path: '/err',
    component: ErrorCard
  },
  {
    path: '',
    redirect: '/server-info-list'
  }
]


routerConfig.initRouterGenerate = new VueRouter({
  routes: routerConfig.routerPath
})



export default routerConfig
