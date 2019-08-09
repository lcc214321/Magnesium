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
      uagDeviceToken: this.$route.query.uagDeviceToken,
      uagAccessToken: this.$route.query.uagAccessToken
    }
  },
  mounted: function() {
    this.checkSsoParam()
  },
  methods: {
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
          if (resp.data.datas == '1') {
            this.$router.push({
              path: '/sms',
              query: {
                uagAppId: this.uagAppId,
                uagAccessToken: this.uagAccessToken,
                uagDeviceToken: this.uagDeviceToken
              }
            })
          } else {
            this.$router.push({
              path: '/password',
              query: {
                uagAppId: this.uagAppId,
                uagAccessToken: this.uagAccessToken,
                uagDeviceToken: this.uagDeviceToken
              }
            })
          }
        }
      )
    }
  }
}
</script>