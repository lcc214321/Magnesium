<template>
  <div>
    <a @click="showDataList()"
      type="primary">添加IP访问白名单</a>

    <Drawer title="ip地址白名单"
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

    <Drawer title="新增白名单"
      :closable="false"
      @on-close="onCloseAddView"
      v-model="showAddFrom">
      <Form :label-width="80"
        ref="addDataParam"
        :rules="validateRule"
        :model="addDataParam">
        <FormItem label="ip地址"
          prop='ipAddress'>
          <i-input placeholder="请输入应用名称"
            v-model="addDataParam.ipAddress"></i-input>
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
export default {
  data() {
    return {
      paramData: {
        data: { ipAddress: '' },
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
        ipAddress: ''
      },
      validateRule: {
        ipAddress: [
          {
            required: true,
            pattern: /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
            message: 'ip地址格式输入不正确',
            trigger: 'blur'
          }
        ]
      },
      showDataListState: false,
      showAddFrom: false,
      columns: [
        {
          title: 'ip白名单',
          key: 'ipAddress'
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
        this.API_PTAH.ipWhtieListFind,
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
          this.utils.netUtil.post(this.$store,this.API_PTAH.ipWhtieListDelete, { id: id }, () => {
            this.listData.splice(index, 1)
            this.$Message.success('删除成功!')
          })
        }
      })
    },
    commitData() {
      this.utils.netUtil.post(this.$store,this.API_PTAH.ipWhtieListAdd, this.addDataParam, () => {
        this.addDataParam.ipAddress = ''
        this.showAddFrom = false
        this.$Message.success('提交成功!')
        this.getDataList()
      })
    },
    onCloseAddView() {
      this.addDataParam.ipAddress = ''
    }
  }
}
</script>

<style>
</style>


