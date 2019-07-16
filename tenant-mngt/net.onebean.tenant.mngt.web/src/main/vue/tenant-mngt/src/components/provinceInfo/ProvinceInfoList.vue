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
          <FormItem prop="provinceName">
            <i-input type="text"
              placeholder="省份名称"
              v-model="paramData.data.provinceName">
            </i-input>
          </FormItem>
        </i-col>

        <i-col span="4"
          offset="1"
          class="page-col">
          <FormItem prop="provinceCode">
            <i-input type="text"
              placeholder="省份编码"
              v-model="paramData.data.provinceCode">
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
        <Button type="warning"
          size="small"
          style="margin-right: 5px"
          @click="showCityEditTab(row.provinceCode,row.provinceName)">城市列表</Button>
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
    <cityInfoList :show-city-tab="showCityTab"
      :provinceCode="editCityPCode" :provinceName="editCityPName"
      @on-close="closeCityEditTab" />
  </div>
</template>
<script>
import cityInfoList from '../cityInfo/CityInfoList'
export default {
  data() {
    return {
      showCityTab: false,
      editCityPCode: '',
      editCityPName: '',
      routerPath: this.$route.path,
      paramData: {
        data: {},
        page: {
          currentPage: 1,
          pageSize: 10
        },
        sort: {
          orderBy: 'pro_sort',
          sort: 'desc'
        }
      },
      columns: [
        { title: '省份编码', key: 'provinceCode' },
        { title: '省份名称', key: 'provinceName' },
        { title: '排序值', key: 'proSort' },
        { title: '创建时间', key: 'createTime' },
        { title: '更新时间', key: 'updateTime' },
        { title: '操作', slot: 'action', width: 250, align: 'center' }
      ],
      tableData: []
    }
  },
  components: {
    cityInfoList
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
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.provinceInfoFind,
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
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.provinceInfoDelete,
        { id: id },
        () => {
          this.tableData.splice(index, 1)
          this.$Message.success('删除成功!')
        }
      )
    },
    handleReset() {
      this.$refs.queryParamFrom.resetFields()
      this.getdata()
    },
    pushAdd() {
      this.$router.push(`${this.$route.path}/province-info-add`)
    },
    pushEditor(id) {
      this.$router.push({
        path: `${this.$route.path}/province-info-editor/${id}`
      })
    },
    showCityEditTab(code,name) {
      this.editCityPCode = code
      this.editCityPName = name
      this.showCityTab = true
    },
    closeCityEditTab() {
      this.showCityTab = false
    }
  }
}
</script>