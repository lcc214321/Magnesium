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
    <Row>
      <i-col span="12"
        offset="6">
        <Form ref="upSteamNodeInfoFrom"
          :model="upSteamNodeInfoFrom"
          :rules="fromValidate"
          :label-width="110">

          <FormItem label="应用节点名称"
            prop="nodeName">
              <Select v-model="upSteamNodeInfoFrom.nodeName"
                filterable
                remote
                :remote-method="queryUpSteamInfo"
                placeholder="请搜索以选择应用节点"
                :loading="isLoadingUpSteamNodeInfo">
                <Option v-for="(option, index) in upSteamNodeInfo"
                  :value="option.upsteamName"
                  :key="index">{{option.upsteamName}}</Option>
              </Select>
              <upSteamName />
            </FormItem>

            <FormItem label="物理地址"
              prop="physicalPath">
              <i-input v-model="upSteamNodeInfoFrom.physicalPath"
                placeholder="请输入物理地址"></i-input>
            </FormItem>

            <FormItem>
              <Button type="primary"
                @click="handleSubmit('upSteamNodeInfoFrom')">提交</Button>
            </FormItem>
        </Form>
      </i-col>
    </Row>
  </div>

</template>


<script>
import upSteamName from '../upSteamName/upSteamName'

export default {
  props: {
    query: { userId: 0 }
  },
  data() {
    return {
      routerPath: this.$route.path,
      isLoadingUpSteamNodeInfo: false,
      upSteamNodeInfo: [],
      upSteamNodeInfoFrom: {
        id: this.$route.params.id,
        nodeName: '',
        physicalPath: ''
      },
      fromValidate: {
        nodeName: [
          {
            required: true,
            max: 30,
            pattern: /^[A-Za-z]+[A-Za-z-]*[A-Za-z]+$/,
            message:
              '应用节点名称不能为空,最多30个字符,必须以英文开头和结尾只能包含中横线 [ - ]',
            trigger: 'blur'
          }
        ],
        physicalPath: [
          {
            pattern: /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):{1}[0-9]{4,}$/,
            required: true,
            message: '物理地址不能为空 并且为标准的ip地址格式+端口',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  components: {
    upSteamName
  },
  mounted: function() {
    this.loadData()
  },
  computed: {
    breadcrumbList: function() {
      return this.utils.routerUtil.initRouterTreeNameArr(this.routerPath)
    }
  },
  methods: {
    queryUpSteamInfo(query) {
      if (query !== '') {
        this.isLoadingUpSteamNodeInfo = true
        this.utils.netUtil.post(this.$store,
          this.API_PTAH.upSteamNameFind,
          {
            data: {
              nodeName: query,
              physicalPath: ''
            },
            page: {
              currentPage: 1,
              pageSize: 10000
            },
            sort: {
              orderBy: 'id',
              sort: 'desc'
            }
          },
          response => {
            this.isLoadingUpSteamNodeInfo = false
            this.upSteamNodeInfo = response.data.datas
          }
        )
      } else {
        this.upSteamNodeInfo = []
      }
    },
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.commitData()
        } else {
          this.$Message.error('请完善表单信息!')
        }
      })
    },
    loadData() {
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.upSteamNodeInfoFindById,
        this.upSteamNodeInfoFrom,
        response => {
          this.upSteamNodeInfoFrom = response.data.datas
        }
      )
    },
    commitData() {
      this.upSteamNodeInfoFrom.id = this.$route.params.id
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.upSteamNodeInfoUpdate,
        this.upSteamNodeInfoFrom,
        () => {
          this.$Message.success('提交成功!')
          this.$router.push('/upsteam-node-info-list')
        }
      )
    }
  }
}
</script>
