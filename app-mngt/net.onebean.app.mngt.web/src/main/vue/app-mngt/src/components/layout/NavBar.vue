<template>
  <Menu mode="horizontal"
    theme="light"
    active-name="1"
    v-if="isShowBar">
    <router-link to="/app-info-list"
      replace>
      <MenuItem name="1">
      <Icon type="ios-construct" />
      应用管理
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