<template>
  <Menu mode="horizontal"
    v-if="isShowBar"
    theme="light"
    :active-name="this.utils.routerUtil.initRouterNavBarActiveStatus(this.$route.path)">
    <router-link to="/tenant-info-list"
      replace>
      <MenuItem name="/tenant-info-list">
      <Icon type="md-key" />
      租户管理
      </MenuItem>
    </router-link>
    <router-link to="/province-info-list"
      replace>
      <MenuItem name="/province-info-list">
      <Icon type="ios-home" />
      城市配置
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
