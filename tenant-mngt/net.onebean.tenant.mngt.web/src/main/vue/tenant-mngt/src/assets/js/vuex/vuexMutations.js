let vuexMutations = {
  loadCurrentLoginUserInfo: async function(state, uagInfo) {
    state.uagCurrentLoginUserInfo = uagInfo
  }
}

export default vuexMutations
