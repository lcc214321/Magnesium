<template>
  <Menu mode="horizontal"
    v-if="isShowBar"
    theme="light"
    :active-name="this.utils.routerUtil.initRouterNavBarActiveStatus(this.$route.path)">
    <router-link to="/uag-userinfo-list"
      replace>
      <MenuItem name="/uag-userinfo-list">
      <Icon type="md-contacts" />
      用户管理
      </MenuItem>
    </router-link>
    <router-link to="/uag-log-list"
      replace>
      <MenuItem name="/uag-log-list">
      <Icon type="md-list" />
      操作日志
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
    return {}
  }
}
</script>
