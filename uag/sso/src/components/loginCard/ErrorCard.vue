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
        <P class="err-msg">页面飞到火星去啦</P>
        <img :src="errImg404"
          class="err-img" />
      </div>
    </i-col>
  </Row>
</template>

<script>
import errImg404Source from '@/assets/img/404.png'

export default {
  data() {
    return {
      errImg404: errImg404Source
    }
  },
  mounted: function() {},
  methods: {}
}
</script>