let vuexMutations = {
  setUserInfoListAppId: function(state,payload) {
    state.userInfoListAppId = payload
  },
  loadCurrentLoginUserInfo: async function(state, uagInfo) {
    state.uagCurrentLoginUserInfo = uagInfo
  }
}

export default vuexMutations
