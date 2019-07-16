<style>
</style>

<template>
  <div>
    <Row>
      <i-col span="24"
        class="bread-crumb">
        <Breadcrumb>
          <BreadcrumbItem v-for="(item,index) in breadcrumbList"
            :to="item.path"
            :key="index">
            <Icon :type="item.icon"></Icon> {{item.name}}
          </BreadcrumbItem>
        </Breadcrumb>
      </i-col>
    </Row>
    <Divider />
    <router-view></router-view>
    <Form ref="queryParamFrom"
      class="query-pram-from"
      :model="paramData.data">

      <Row>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="serverName">
            <i-input type="text"
              placeholder="服务名称"
              v-model="paramData.data.serverName">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="deployType">
            <Select v-model="paramData.data.deployType"
              style="width:200px"
              placeholder="部署类型"
              clearable>
              <Option v-for="item in deployTypeEunmArr"
                :value="item.value"
                :disabled="item.disabled"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>
        </i-col>

        <i-col span="4"
          offset="18"
          class="button-group">
          <Button type="info"
            @click="pushAdd()">新增</Button>
          <Button type="success"
            @click="getdata()">查询</Button>
          <Button type="warning"
            @click="handleReset()">重置</Button>
          <Button type="primary"
            @click="syncAppApiRelationship()">同步</Button>
        </i-col>
      </Row>
    </Form>

    <br />
    <Table border
      :columns="columns"
      :data="tableData"
      class="list-button-group">
      <template slot-scope="{ row, index }"
        slot="action">
        <Button type="warning"
          size="small"
          style="margin-right: 5px"
          @click="pushApiList(row.id)">接口管理</Button>
        <Button type="primary"
          size="small"
          style="margin-right: 5px"
          @click="pushEditor(row.id)">编辑服务</Button>
        <Button type="error"
          size="small"
          @click="deleteData(row.id ,index)">删除服务</Button>
      </template>
    </Table>
    <Row>
      <i-col span="6"
        offset="17"
        class="page-col">
        <Page :total="paramData.data.totalCount"
          :current="paramData.data.currentPage"
          :page-size="paramData.data.pageSize"
          @on-change="handlePage"
          @on-page-size-change='handlePageSize'
          show-sizer
          class="pagination" />
      </i-col>
    </Row>

  </div>
</template>
<script>


export default {
  data() {
    return {
      routerPath: this.$route.path,
      deployTypeEunmArr: [
        {
          value: '0',
          label: '物理地址部署'
        },
        {
          value: '1',
          label: 'marathon部署',
          disabled: true
        }
      ],
      paramData: {
        data: {
          serverName: '',
          deployType: ''
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
      columns: [
        { title: '服务名称', key: 'serverName' },
        { title: '部署类型', key: 'deployType' },
        { title: '应用节点', key: 'hostPath' },
        { title: '创建时间', key: 'createTime' },
        { title: '修改时间', key: 'updateTime' },
        { title: '操作人', key: 'operatorName' },
        { title: '操作', slot: 'action', width: 250, align: 'center' }
      ],
      tableData: []
    }
  },
  computed: {
    breadcrumbList: function() {
      return this.utils.routerUtil.initRouterTreeNameArr(this.routerPath)
    }
  },
  mounted: function() {
    this.getdata()
  },
  methods: {
    handlePage(value) {
      this.paramData.page.currentPage = value
      this.getdata()
    },
    handlePageSize(value) {
      this.paramData.page.pageSize = value
      this.getdata()
    },
    rowClassName(row, index) {
      return this.utils.styleUtil.initTableListRowClass(index)
    },
    getdata() {
      this.utils.netUtil.post(this.$store,this.API_PTAH.serverInfoFind, this.paramData, response => {
        this.tableData = response.data.datas
        this.paramData.data.totalCount = response.data.page.totalCount
        this.paramData.data.pageSize = response.data.page.pageSize
        this.paramData.data.currentPage = response.data.page.currentPage
      })
    },
    deleteData(id, index) {
      this.$Modal.confirm({
        title: '警告',
        content: '确认删除该条数据吗',
        onOk: () => {
          this.utils.netUtil.post(this.$store,this.API_PTAH.serverInfoDelete, { id: id }, response => {
            response.data
            this.tableData.splice(index, 1)
            this.$Message.success('删除成功!')
          })
        }
      })
    },
    syncAppApiRelationship() {
      this.utils.netUtil.post(this.$store,this.API_PTAH.syncAppApiRelationship, {}, () => {
        this.$Message.success('同步数据成功!')
      })
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      this.$router.push(`${this.$route.path}/server-info-add`)
    },
    pushEditor(id) {
      this.$router.push({
        path: `${this.$route.path}/server-info-editor/${id}`
      })
    },
    pushApiList(id) {
      this.$router.push({ path: `${this.$route.path}/api-info-list/${id}` })
    }
  }
}
</script>