let frameUtil = {}

frameUtil.sendAutoParentFrameSizeMsg = () => {
  setInterval(() => {
    const subFrameHeight = window.document.body.offsetHeight
    window.parent.postMessage(
      { subFrameHeight: subFrameHeight },'*'
    )
  }, 500)
}

frameUtil.reSizeParentFrameSize = (store) => {
  window.addEventListener('message',  (e) => {
      var subframeHeight = e.data.subFrameHeight
      if (typeof subframeHeight != 'undefined') {
        subframeHeight = `${subframeHeight}px`
        store.commit('reSizeFrameHeight', subframeHeight)
      }
    },
    false
  )
}

export default frameUtil
