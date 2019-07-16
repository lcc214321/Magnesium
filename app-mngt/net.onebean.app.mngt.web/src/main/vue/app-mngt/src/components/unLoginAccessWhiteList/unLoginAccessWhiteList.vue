<template>
  <div>
    <a @click="showDataList()"
      type="primary">添加免登录访问接口白名单</a>

    <Drawer title="免登录访问接口白名单"
      width="512"
      :closable="false"
      v-model="showDataListState">
      <Button @click="showAddFrom = true"
        type="info">新增</Button>
      <Divider />
      <Table :columns="columns"
        :data="listData">
        <template slot-scope="{ row, index }"
          slot="action">
          <a @click="deleteData(row.id ,index)">删除</a>
        </template>
      </Table>
    </Drawer>

    <Drawer title="新增免登录访问接口白名单"
      :closable="false"
      @on-close="onCloseAddView"
      v-model="showAddFrom">
      <Form :label-width="82"
        ref="addDataParam"
        :rules="validateRule"
        :model="addDataParam">
        <FormItem label="白名单接口"
          prop='apiId'>
          <Select ref="ApiSelector"
            :model="addDataParam.apiId"
            placeholder="搜索接口名称"
            filterable
            remote
            :remote-method="queryApiByAppId"
            :loading="apiOptionsLoading"
            @on-clear="clearSelectApi"
            :clearable="true"
            @on-change="changeSelectApi">
            <Option v-for="(option, index) in apiOptions"
              :value="option.apiId"
              :key="index">{{option.apiName}}</Option>
          </Select>
        </FormItem>
        <FormItem>
          <Button type="primary"
            @click="handleSubmit('addDataParam')">提交</Button>
        </FormItem>
      </Form>
    </Drawer>
  </div>
</template>

<script>
import _ from 'lodash'

export default {
  props: ['appId'],
  data() {
    return {
      apiOptionsLoading: false,
      apiOptions: [],
      paramData: {
        data: {
          apiId: '',
          apiName: '',
          apiPath: '',
          appId: this.appId
        },
        page: {
          currentPage: 1,
          pageSize: 10
        },
        sort: {
          orderBy: 'id',
          sort: 'desc'
        }
      },
      addDataParam: {
        apiId: '',
        apiName: '',
        apiPath: '',
        appId: this.appId
      },
      validateRule: {
        apiId: [
          {
            required: true,
            message: '请搜索接口名称并选中',
            trigger: 'change',
            type: 'number'
          }
        ]
      },
      showDataListState: false,
      showAddFrom: false,
      columns: [
        {
          title: '接口名称',
          key: 'apiName'
        },
        { title: '操作', slot: 'action', width: 250, align: 'center' }
      ],
      listData: []
    }
  },
  mounted: function() {},
  methods: {
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.commitData()
        } else {
          this.$Message.error('请完善表单信息!')
        }
      })
    },
    showDataList() {
      this.showDataListState = true
      if (this.listData.length == 0) {
        this.getDataList()
      }
    },
    getDataList() {
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.unLoginAccessWhiteListFind,
        this.paramData,
        response => {
          this.listData = response.data.datas
        }
      )
    },
    deleteData(id, index) {
      this.$Modal.confirm({
        title: '警告',
        content: '确认删除该条数据吗',
        onOk: () => {
          this.utils.netUtil.post(this.$store,
            this.API_PTAH.unLoginAccessWhiteListDelete,
            { id: id },
            () => {
              this.listData.splice(index, 1)
              this.$Message.success('删除成功!')
            }
          )
        }
      })
    },
    commitData() {
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.unLoginAccessWhiteListAdd,
        this.addDataParam,
        () => {
          this.showAddFrom = false
          this.$Message.success('提交成功!')
          this.getDataList()
          this.onCloseAddView()
        }
      )
    },
    onCloseAddView() {
      this.clearSelectApi()
      this.$refs.ApiSelector.clearSingleSelect()
    },
    changeSelectApi(target) {
      if (target) {
        this.apiOptions.map(item => {
          if (item.apiId === target) {
            this.addDataParam.apiId = target
            this.addDataParam.apiName = item.apiName
            this.addDataParam.apiPath = item.apiPath
          }
        })
      }
    },
    queryApiByAppId: _.debounce(function(query) {
      if (query !== '') {
        this.apiOptionsLoading = true
        this.apiOptions = []
        this.utils.netUtil.post(this.$store,
          this.API_PTAH.unLoginAccessWhiteListFindUnBindingData,
          {
            data: {
              appId: this.appId,
              apiName: query
            }
          },
          response => {
            this.apiOptionsLoading = false
            this.apiOptions = response.data.datas
          }
        )
      } else {
        this.apiOptions = []
      }
    }, 500),
    clearSelectApi() {
      this.addDataParam.apiId = ''
      this.addDataParam.apiName = ''
      this.addDataParam.apiPath = ''
      this.addDataParam.apiId = ''
      this.apiOptions = []
    }
  }
}
</script>

<style>
</style>


