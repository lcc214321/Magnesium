<template>
  <div>
    <Drawer :title="provinceName+' 城市列表'"
      width="512"
      :closable="false"
      @on-close="onCloseTabView"
      @on-visible-change="onloadTabView"
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

    <Drawer title="新增城市"
      :closable="false"
      @on-close="onCloseAddView"
      v-model="showAddFrom">
      <Form :label-width="80"
        ref="addDataParam"
        :rules="validateRule"
        :model="addDataParam">

        <FormItem label="城市名称"
          prop='cityName'>
          <i-input placeholder="请输入城市名称"
            v-model="addDataParam.cityName"></i-input>
        </FormItem>

        <FormItem label="排序字段"
          prop='citySort'>
          <i-input placeholder="请输入排序字段"
            v-model="addDataParam.citySort"></i-input>
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
  props: ['showCityTab', 'provinceCode','provinceName'],
  data() {
    return {
      paramData: {
        data: {
          provinceCode: this.provinceCode,
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
          provinceCode: this.provinceCode,
          level: 1,
          citySort: '',
          cityName: ''
      },
      validateRule: {
        cityName: [
          {
            required: true,
            message: '城市名称不能为空,最多10个字符',
            max: 20,
            trigger: 'blur'
          }
        ],
        citySort: [
          {
            required: true,
            pattern: /^[0-9]*$/,
            max: 5,
            message: '只能填写数字,不能超过5位数',
            trigger: 'blur'
          }
        ]
      },
      showDataListState: false,
      showAddFrom: false,
      columns: [
        {
          title: '城市名',
          key: 'cityName'
        },
        {
          title: '城市编码',
          key: 'cityCode'
        },
        { title: '操作', slot: 'action', width: 250, align: 'center' }
      ],
      listData: []
    }
  },
  watch: {
    showCityTab: function() {
      this.showDataListState = this.showCityTab
    }
  },
  mounted: function() {
    this.getDataList()
  },
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
    getDataList() {
      this.paramData.data.provinceCode = this.provinceCode
      this.utils.netUtil.post(this.$store,this.API_PTAH.cityInfoFind, this.paramData, response => {
        this.listData = response.data.datas
      })
    },
    deleteData(id, index) {
      this.$Modal.confirm({
        title: '警告',
        content: '确认删除该条数据吗',
        onOk: () => {
          this.utils.netUtil.post(this.$store,this.API_PTAH.cityInfoDelete, { id: id }, () => {
            this.listData.splice(index, 1)
            this.$Message.success('删除成功!')
          })
        }
      })
    },
    commitData() {
      this.addDataParam.provinceCode = this.provinceCode
      this.utils.netUtil.post(this.$store,this.API_PTAH.cityInfoAdd, this.addDataParam, () => {
        this.addDataParam.ipAddress = ''
        this.showAddFrom = false
        this.$Message.success('提交成功!')
        this.getDataList()
        this.onCloseAddView()
      })
    },
    onCloseAddView() {
      this.addDataParam.citySort = ''
      this.addDataParam.cityName = ''
    },
    onCloseTabView() {
      this.$emit('on-close')
    },
    onloadTabView(status) {
      if (status) {
        this.getDataList()
      }
    }
  }
}
</script>

<style>
</style>


