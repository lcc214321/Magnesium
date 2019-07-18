<style scoped>
.dev-header {
  width: 100%;
  height: 50px;
  padding: 0 40px;
  box-sizing: border-box;
  z-index: 1000;
  position: fixed;
  top: 0;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.1);
  font-size: 14px;
}
.layout-logo {
  width: 30px;
  height: 30px;
  background: #fff;
  border-radius: 3px;
  float: left;
  position: relative;
  left: 20px;
  background-repeat: no-repeat;
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;

  margin: 0 auto;
  position: relative;
  top: 50%;
  margin-top: -15px;
}
.layout-logo-title {
  width: 180px;
  height: 30px;
  margin: 0 auto;
  position: relative;
  top: 50%;
  margin-top: -30px;
  float: left;
  margin-left: 30px;
}
.logo-title {
  font-size: 22px;
  font-weight: bold;
  color: rgb(97, 97, 97);
}
</style>

<template>
  <Header class="dev-header">
    <div class="layout-logo" :style="{backgroundImage: 'url(' + logoImg + ')' }"></div>
    <div class="layout-logo-title"><span class="logo-title">单点登录系统</span></div>
  </Header>

</template>

<script>
import logoImgResource from '@/assets/img/logo.png'
export default {
  data() {
    return {
      logoImg: logoImgResource
    }
  }
}
</script>