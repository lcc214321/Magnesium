<template>
  <Modal v-model="showallApiModal"
    width="830"
    @on-ok="closeModal"
    @on-cancel="closeModal"
    @on-visible-change="initModal"
    :title="modalTitle">
    <Row>
      <i-col span="20"
        offset="2">
        <Select ref="serverSelector"
          placeholder="搜索并选择服务名"
          filterable
          :clearable="true"
          remote
          @on-clear="clearSelectServer"
          @on-change="changeSelectServer"
          :remote-method="queryServerbyName"
          :loading="serverOptionsLoading">
          <Option v-for="(option, index) in serverOptions"
            :value="option.id"
            :key="index">{{option.serverName}}</Option>
        </Select>
      </i-col>
    </Row>
    <Divider />
    <Row>
      <i-col span="20"
        offset="2">
        <Transfer :data="allApi"
          :titles="transferTitle"
          :target-keys="bindKeys"
          :list-style="transferStyle"
          @on-change="handleBindingApi"></Transfer>
      </i-col>
    </Row>
    <Spin size="large"
      fix
      v-if="loadingModalData"></Spin>
  </Modal>
</template>


<script>
import _ from 'lodash'

export default {
  props: ['appId', 'showModal', 'modalTitle'],
  data() {
    return {
      loadingModalData: false,
      transferStyle: {
        width: '300px',
        height: '300px'
      },
      transferTitle: ['绑定的API', '未绑定的API'],
      showallApiModal: this.showModal,
      allApi: [],
      bindKeys: [],
      serverOptions: [],
      serverOptionsLoading: false,
      queryServerbyNameParam: {
        serverId: '',
        appId: this.appId
      }
    }
  },
  watch: {
    showModal: function() {
      this.showallApiModal = this.showModal
    }
  },
  methods: {
    clearSelectServer() {
      this.allApi = []
      this.bindKeys = []
      this.queryServerbyNameParam.appId = this.appId
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.findApiByAppId,
        this.queryServerbyNameParam,
        response => {
          this.allApi = response.data.datas.allApi
          this.bindKeys = response.data.datas.bindKeys
        }
      )
    },
    closeModal: function() {
      this.$refs.serverSelector.clearSingleSelect()
      this.allApi = []
      this.bindKeys = []
      this.$emit('closeApiBindModal')
    },
    initModal: function(target) {
      if (target) {
        this.queryServerbyNameParam.appId = this.appId
        this.loadingModalData = true
        this.utils.netUtil.post(this.$store,
          this.API_PTAH.findApiByAppId,
          this.queryServerbyNameParam,
          response => {
            this.loadingModalData = false
            this.allApi = response.data.datas.allApi
            this.bindKeys = response.data.datas.bindKeys
          }
        )
      }
    },
    pushToHeavyAllApi(originalArr, targetArr) {
      targetArr.forEach(function(target) {
        let repeat = false
        originalArr.forEach(function(original) {
          if (original.key == target.key) {
            repeat = true
          }
        })
        if (!repeat) {
          originalArr.push(target)
        }
      })
      return originalArr
    },
    pushToHeavyBindKeys(originalArr, targetArr) {
      targetArr.forEach(function(target) {
        let repeat = false
        originalArr.forEach(function(original) {
          if (original == target) {
            repeat = true
          }
        })
        if (!repeat) {
          originalArr.push(target)
        }
      })
      return originalArr
    },
    changeSelectServer(target) {
      if (target) {
        this.queryServerbyNameParam.serverId = target
        this.queryServerbyNameParam.appId = this.appId
        this.utils.netUtil.post(this.$store,
          this.API_PTAH.findAppApiRelationshipByServerIdAndAppId,
          this.queryServerbyNameParam,
          response => {
            this.allApi = this.pushToHeavyAllApi(
              this.allApi,
              response.data.datas.allApi
            )
            this.bindKeys = this.pushToHeavyBindKeys(
              this.bindKeys,
              response.data.datas.bindKeys
            )
          }
        )
      }
    },
    handleBindingApi(newTargetKeys, direction, moveKeys) {
      if (direction === 'left') {
        this.utils.netUtil.post(this.$store,
          this.API_PTAH.bindingApi,
          { appId: this.appId, apiIds: moveKeys },
          () => {
            this.$Message.success('绑定成功!')
          }
        )
      }

      if (direction === 'right') {
        this.utils.netUtil.post(this.$store,
          this.API_PTAH.unBindingApi,
          { appId: this.appId, apiIds: moveKeys },
          () => {
            this.$Message.success('解绑成功!')
          }
        )
      }

      this.bindKeys = newTargetKeys
    },
    queryServerbyName: _.debounce(function(query) {
      if (query !== '') {
        this.serverOptionsLoading = true
        this.serverOptions = []
        this.loadingModalData = true
        this.utils.netUtil.post(this.$store,
          this.API_PTAH.findServerByName,
          { serverName: query },
          response => {
            this.serverOptionsLoading = false
            this.serverOptions = response.data.datas
            this.loadingModalData = false
          }
        )
      } else {
        this.serverOptions = []
      }
    }, 500)
  }
}
</script>
