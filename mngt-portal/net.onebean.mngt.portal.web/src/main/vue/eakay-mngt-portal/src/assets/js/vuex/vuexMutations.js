let vuexMutations = {
  reSizeFrameHeight: function(state,appFrameHeight) {
    state.appFrameHeight = appFrameHeight
  },
  doLoadingMenu: function(state) {
    state.isLoadMenu = true
  },
  loadCurrentLoginUserInfo:async function(state,uagInfo){
    state.uagCurrentLoginUserInfo = uagInfo
  }
}

export default vuexMutations
