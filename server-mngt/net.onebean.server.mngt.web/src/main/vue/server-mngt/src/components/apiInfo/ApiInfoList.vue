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
          <FormItem prop="apiName">
            <i-input type="text"
              placeholder="接口名称"
              v-model="paramData.data.apiName">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="apiUri">
            <i-input type="text"
              placeholder="接口地址"
              v-model="paramData.data.apiUri">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="3"
          offset="19"
          class="button-group">
          <Button type="info"
            @click="pushAdd()">新增</Button>
          <Button type="success"
            @click="getdata()">查询</Button>
          <Button type="warning"
            @click="handleReset()">重置</Button>
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
        <Button type="primary"
          size="small"
          style="margin-right: 5px"
          @click="pushEditor(row.id)">编辑</Button>
        <Button type="error"
          size="small"
          @click="deleteData(row.id ,index)">删除</Button>
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
      apiStatusEunmArr: [
        {
          value: '0',
          label: '未启用'
        },
        {
          value: '1',
          label: '启用'
        }
      ],
      paramData: {
        data: {
          serverId: this.$route.params.sid,
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
        { title: '接口名称', key: 'apiName' },
        { title: '接口地址', key: 'apiUri' },
        { title: '代理地址', key: 'proxyPath' },
        { title: '接口状态', key: 'apiStatus' },
        { title: '创建时间', key: 'createTime' },
        { title: '修改时间', key: 'updateTime' },
        { title: '操作人', key: 'operatorName' },
        { title: '操作', slot: 'action', width: 225, align: 'center' }
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
      this.utils.netUtil.post(this.$store,this.API_PTAH.apiInfoFind, this.paramData, response => {
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
          this.utils.netUtil.post(this.$store,this.API_PTAH.apiInfoDelete, { id: id }, response => {
            response.data
            this.tableData.splice(index, 1)
            this.$Message.success('删除成功!')
          })
        }
      })
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      this.$router.push(
        `/server-info-list/api-info-list/api-info-add/${this.$route.params.sid}`
      )
    },
    pushEditor(id) {
      this.$router.push({
        path: `/server-info-list/api-info-list/api-info-editor/${
          this.$route.params.sid
        }/${id}`
      })
    },
    pushApiList() {
      this.$router.push(`${this.$route.path}/api-info-list`)
    }
  }
}
</script>