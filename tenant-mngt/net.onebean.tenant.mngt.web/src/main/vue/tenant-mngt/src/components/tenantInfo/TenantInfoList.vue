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
    <Form ref="queryParamFrom"
      class="query-pram-from"
      :model="paramData.data">

      <Row>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="tenantName">
            <i-input type="text"
              placeholder="租户名称"
              v-model="paramData.data.tenantName">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="principal">
            <i-input type="text"
              placeholder="负责人"
              v-model="paramData.data.principal">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="mobile">
            <i-input type="text"
              placeholder="手机号码"
              v-model="paramData.data.mobile">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="email">
            <i-input type="text"
              placeholder="登录邮箱"
              v-model="paramData.data.email">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="5"
          offset="19"
          class="button-group">
          <Button type="info"
            @click="pushAdd()">新增</Button>
          <Button type="success"
            @click="getdata()">查询</Button>
          <Button type="warning"
            @click="handleReset()">重置</Button>
          <Button type="primary"
            @click="syncData()">同步激活账号</Button>
        </i-col>
      </Row>
    </Form>

    <br />
    <Table border
      :columns="columns"
      :data="tableData"
      class="list-button-group">
      <!-- <template slot-scope="{ row, index }" -->
      <template slot-scope="{ row }"
        slot="action">
        <Button type="warning"
          size="small"
          style="margin-right: 5px"
          v-show="showOnNeedInit(row.status)"
          @click="tenantInfoInitAccount(row.id)">激活</Button>
        <Button type="primary"
          size="small"
          style="margin-right: 5px"
          @click="pushEditor(row.id)">编辑</Button>
        <!-- <Button type="error"
          size="small"
          @click="deleteData(row.id ,index)">删除</Button> -->
      </template>
    </Table>
    <Row>
      <i-col span="6"
        offset="18"
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
      paramData: {
        data: {},
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
        { title: '租户名称', key: 'tenantName' },
        { title: '登录邮箱', key: 'email' },
        { title: '负责人', key: 'principal' },
        { title: '手机号码', key: 'mobile' },
        { title: '所在省', key: 'provinceCode' },
        { title: '所在市', key: 'cityCode' },
        { title: '账号状态', key: 'status' },
        { title: '创建时间', key: 'createTime' },
        { title: '操作', slot: 'action', width: 150, align: 'center' }
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
    showOnNeedInit: function(status) {
      return status == '注册'
    },
    handlePage(value) {
      this.paramData.page.currentPage = value
      this.getdata()
    },
    tenantInfoInitAccount(id) {
      this.utils.netUtil.post(this.$store,this.API_PTAH.tenantInfoInitAccount, { id: id }, () => {
        this.$Message.success('初始化事件已成功发出!')
        setTimeout(() => {
            this.getdata()
        }, 2000)
      })
    },
    handlePageSize(value) {
      this.paramData.page.pageSize = value

    },
    rowClassName(row, index) {
      return this.utils.styleUtil.initTableListRowClass(index)
    },
    getdata() {
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.tenantInfoFind,
        this.paramData,
        response => {
          this.tableData = response.data.datas
          this.paramData.data.totalCount = response.data.page.totalCount
          this.paramData.data.pageSize = response.data.page.pageSize
          this.paramData.data.currentPage = response.data.page.currentPage
        }
      )
    },
    deleteData(id, index) {
      this.utils.netUtil.post(this.$store,this.API_PTAH.tenantInfoDelete, { id: id }, response => {
        response.data
        this.tableData.splice(index, 1)
        this.$Message.success('删除成功!')
      })
    },
    syncData() {
      this.utils.netUtil.post(this.$store,this.API_PTAH.tenantInfoSync, {}, () => {
        this.$Message.success('同步数据已发送!')
      })
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      this.$router.push(`${this.$route.path}/tenant-info-add`)
    },
    pushEditor(id) {
      this.$router.push({
        path: `${this.$route.path}/tenant-info-editor/${id}`
      })
    }
  }
}
</script>