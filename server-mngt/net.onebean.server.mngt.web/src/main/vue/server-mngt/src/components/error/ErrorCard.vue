<style scoped>
.err-info-box {
  width: 350px;
  margin: 120px auto;
}
.err-img {
  display: block;
  margin: 0 auto;
}
.err-msg {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  margin: 30px auto;
}
</style>

<template>

  <Row>
    <i-col span="2"
      offset="10">
      <div class="err-info-box">
        <P class="err-msg">访问权限检测中</P>
        <img :src="errImg401"
          class="err-img" />
      </div>
    </i-col>
  </Row>
</template>

<script>
import errImg401Source from '@/assets/img/401.png'

export default {
  data() {
    return {
      errImg401: errImg401Source
    }
  },
  computed: {
    isLogin: function() {
      return Object.keys(this.$store.state.uagCurrentLoginUserInfo).length > 0
    }
  },
  mounted: function() {
    const interval = setInterval(() => {
      if (this.isLogin) {
        clearInterval(interval)
        this.$router.push('/')
      }
    }, 1500)
  },
  methods: {}
}
</script>