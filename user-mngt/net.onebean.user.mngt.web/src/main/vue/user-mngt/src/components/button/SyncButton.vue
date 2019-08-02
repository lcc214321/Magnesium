<style>
</style>

<template>
    <Button type="error" size="small" v-show="showOnUnLock(row)" @click="toggleIsLockStatus(row)">锁定</Button>
</template>

<script>
export default {
  components: {},
  data() {
    return {

    }
  },
  computed: {

  },
  mounted: function() {
  },
  methods: {
   
  }
}
</script>