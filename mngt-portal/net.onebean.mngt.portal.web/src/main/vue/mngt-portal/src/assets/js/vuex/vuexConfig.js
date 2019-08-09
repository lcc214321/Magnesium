import Vuex from 'vuex'
import Vue from 'vue'
import vuexState from './vuexState'
import vuexMutations from './vuexMutations'

Vue.use(Vuex)

let vuexConfig = {}

vuexConfig.initsStoreInstance = new Vuex.Store({
  state: vuexState,
  mutations: vuexMutations
})


export default vuexConfig