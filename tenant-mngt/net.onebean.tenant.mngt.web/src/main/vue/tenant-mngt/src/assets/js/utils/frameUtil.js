import apiPath from '../constant/ApiPath'
import utils from '../utils/utils'



let frameUtil = {}

frameUtil.sendAutoParentFrameSizeMsg = () => {
  setInterval(() => {
    const subFrameHeight = window.document.body.offsetHeight
    const subFrameHost = window.location.host
    const param = {subFrameHeight: subFrameHeight, subFrameHost: subFrameHost}
    window.parent.postMessage(param,'*')
  }, 500)
}

frameUtil.getCurrentLoginUserInfo = (store) => {
  window.addEventListener('message',  (e) => {
      if (typeof(e.data.loadCurrentLoginUserInfo)  != 'undefined') {
        store.commit('loadCurrentLoginUserInfo',e.data.loadCurrentLoginUserInfo) 
      }
    },
    false
  )
}


frameUtil.reSizeParentFrameSize = (store) => {
  window.addEventListener('message',(message) => {
    let subframeHeight = message.data.subFrameHeight
      if (typeof subframeHeight != 'undefined') {
        subframeHeight = `${subframeHeight}px`
        store.commit('reSizeFrameHeight', subframeHeight)
      }
      const subFrameHost = message.data.subFrameHost
      if (typeof subFrameHost != 'undefined') {
        const frames = document.getElementsByTagName('iframe')[0]
        const uagCurrentLoginUserInfo =  store.state.uagCurrentLoginUserInfo
        if(Object.keys(uagCurrentLoginUserInfo).length == 0){
            utils.netUtil.post(apiPath.getCurrentUagLoginInfo,{},(resp) => {
              store.commit('loadCurrentLoginUserInfo',resp.data.datas) 
              frames.contentWindow.postMessage({loadCurrentLoginUserInfo:store.state.uagCurrentLoginUserInfo},'*')
            })
          }else{
          frames.contentWindow.postMessage({loadCurrentLoginUserInfo:store.state.uagCurrentLoginUserInfo},'*')
        }
      }
  },false)
}


export default frameUtil
