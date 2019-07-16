<style scoped>
.dev-article-bg {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: -1;
  background-repeat: no-repeat;
  background-size: 100%;
  background-attachment: fixed;
}
</style>
<template>
  <Layout class="dev-article-bg"
    :style="{backgroundImage: 'url(' + bgUrl + ')' }">
    <NavBar />
    <Content>
      <router-view></router-view>
    </Content>
  </Layout>
</template>
<script>
import logoImgResource from '@/assets/img/bg.svg'

import NavBar from './NavBar'
export default {
  components: {
    NavBar
  },
  data() {
    return {
      bgUrl: logoImgResource,
      uagAppId: this.$route.query.uagAppId,
      uagAccessToken: this.$route.query.uagAccessToken
    }
  },
  mounted: function() {
    this.checkSsoParam()
    this.initDeviceToken()
  },
  methods: {
    initDeviceToken: function() {
      if ('undefined' === typeof localStorage.deviceToken) {
        this.utils.netUtil.postWithAccessToken(
          this.uagAppId,
          this.uagAccessToken,
          this.API_PTAH.authInitializeDevice,
          {},
          resp => {
            localStorage.setItem('deviceToken', resp.data.datas)
          }
        )
      }
    },
    checkSsoParam: function() {
      if (
        typeof this.uagAppId === 'undefined' ||
        typeof this.uagAccessToken === 'undefined'
      ) {
        this.$router.push('/err')
        return
      }
      this.routeLoginCardByLoginType()
    },
    routeLoginCardByLoginType: function() {
      this.utils.netUtil.postWithAccessToken(
        this.uagAppId,
        this.uagAccessToken,
        this.API_PTAH.authGetCurrentAppInfo,
        {},
        resp => {
          if (resp.data.errCode != '0') {
            this.$router.push('/err')
            return
          }
          sessionStorage.setItem('uagAppId', this.uagAppId)
          sessionStorage.setItem('uagAccessToken', this.uagAccessToken)
          if (resp.data.datas == '1') {
            this.$router.push('/sms')
          } else {
            this.$router.push('/password')
          }
        }
      )
    }
  }
}
</script>