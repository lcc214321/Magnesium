<style scoped>
.count-down-button {
  -webkit-appearance: button;
  color: #fff;
  background-color: #2d8cf0;
  border-color: #2d8cf0;
  width: 100%;
  outline: 0;
  display: inline-block;
  margin-bottom: 0;
  font-weight: 400;
  text-align: center;
  vertical-align: middle;
  touch-action: manipulation;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  white-space: nowrap;
  line-height: 1.5;
  user-select: none;
  padding: 5px 15px 6px;
  font-size: 12px;
  border-radius: 4px;
  transition: color 0.2s linear, background-color 0.2s linear,
    border 0.2s linear, box-shadow 0.2s linear;
  font-family: inherit;
  margin: 0;
  text-transform: none;
  overflow: visible;
  box-sizing: border-box;
  -webkit-tap-highlight-color: transparent;
  align-items: flex-start;
  text-rendering: auto;
  letter-spacing: normal;
  word-spacing: normal;
  text-indent: 0px;
  text-shadow: none;
  font: 400 13.3333px Arial;
  -webkit-font-smoothing: antialiased;
  margin-top: 24px;
}
.count-down-button:disabled {
  background-color: #6aaffa;
  border-color: #6aaffa;
  color: #e6e6e6;
}
</style>

<template>
  <button class="count-down-button"
    :disabled="countDownButtonDisabled"
    type="button">{{content}}
  </button>
</template>

<script>
export default {
  props: ['countDown', 'showText', 'reShowText'],
  data() {  
    return {
      content: this.showText,
      cg: this.countDown,
      countDownButtonDisabled: false
    }
  },
  computed: {},
  methods: {
    changeCountNumber() {
      this.countDownButtonDisabled = 'disabled'
      const interval = setInterval(() => {
        if (this.cg > -1) {
          this.content = this.cg
          this.cg--
        } else {
          this.content = this.reShowText
          this.countDownButtonDisabled = false
          this.cg = this.countDown
          clearInterval(interval)
        }
      }, 1000)
    }
  }
}
</script>