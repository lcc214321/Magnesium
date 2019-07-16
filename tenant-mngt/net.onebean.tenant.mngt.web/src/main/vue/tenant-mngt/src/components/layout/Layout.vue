<style scoped>
.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
}
.layout-logo {
  width: 100px;
  height: 30px;
  background: #5b6270;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
}
.layout-nav {
  width: 420px;
  margin: 0 auto;
  margin-right: 20px;
}
.layout-footer-center {
  text-align: center;
}
</style>
<template>
  <div class="layout">
    <Layout>
      <NavBar />
      <Content :style="{margin: '20px 20px 0', background: '#fff', minHeight: '720px'}">
        <keep-alive>
          <router-view></router-view>
        </keep-alive>
      </Content>
    </Layout>
  </div>
</template>
<script>
import NavBar from './NavBar.vue'

export default {
  components: {
    NavBar
  },
  mounted: function() {
    this.utils.frameUtil.getCurrentLoginUserInfo(this.$store)
    this.utils.frameUtil.sendAutoParentFrameSizeMsg()
  }
}
</script>