<template>
  <Menu mode="horizontal"
    v-if="isShowBar"
    theme="light"
    :active-name="this.utils.routerUtil.initRouterNavBarActiveStatus(this.$route.path)">
    <router-link to="/server-info-list"
      replace>
      <MenuItem name="/server-info-list">
      <Icon type="md-analytics" /> 服务管理
      </MenuItem>
    </router-link>
    <router-link to="/nginx-node-info-list"
      replace>
      <MenuItem name="/nginx-node-info-list">
      <Icon type="ios-aperture" />
      网关节点管理
      </MenuItem>
    </router-link>
    <router-link to="/upsteam-node-info-list"
      replace>
      <MenuItem name="/upsteam-node-info-list">
      <Icon type="md-cloud-done" />
      应用节点管理
      </MenuItem>
    </router-link>
  </Menu>
</template>
<script>
export default {
  computed: {
    isShowBar: function() {
      return this.$route.path != '/err'
    }
  },
  data() {
    return {
    }
  }
}
</script>
