<style>
.appFrame {
  border: none;
}
</style>

<template>
  <Layout :style="{height: frameHeight}">
   <iframe :src="pcUrl" 
    class="appFrame"
    scrolling="auto"
    width='100%'
    height='100%'
    frameborder='0' />
  </Layout>


</template>


<script>
export default {
  data() {
    return {}
  },
  components: {},
  computed: {
    breadcrumbList: function() {
      return this.utils.initRouterTreeNameArr(this.routerPath)
    },
    pcUrl: function() {
      return this.utils.routerUtil.getPcUrlByRouterPath(this.$route.path)
    },
    frameHeight() {
      return this.$store.state.appFrameHeight
    }
  },
  mounted: function() {},
  methods: {}
}
</script>