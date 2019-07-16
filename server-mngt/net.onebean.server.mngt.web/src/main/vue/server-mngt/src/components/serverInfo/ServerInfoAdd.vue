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
        <Form ref="serverInfoFrom"
          :model="serverInfoFrom"
          :rules="serverInfoFromValidate"
          :label-width="80">

          <FormItem label="服务名称"
            prop="serverName">
            <i-input v-model="serverInfoFrom.serverName"
              placeholder="请输入服务名称"></i-input>
          </FormItem>

          <FormItem label="部署类型"
            prop="deployType">
            <Select v-model="serverInfoFrom.deployType"
              placeholder="选择部署类型">
              <Option v-for="item in deployTypeEunmArr"
                :value="item.value"
                :disabled="item.disabled"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem label="应用节点"
            prop="hostPath">
            <Select v-model="serverInfoFrom.hostPath"
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

          <FormItem>
            <Button type="primary"
              @click="handleSubmit('serverInfoFrom')">提交</Button>
            <Button @click="handleReset('serverInfoFrom')"
              style="margin-left: 8px">重置</Button>
          </FormItem>
        </Form>
      </i-col>
    </Row>
  </div>

</template>


<script>
import upSteamName from '../upSteamName/upSteamName'

export default {
  data() {
    return {
      isLoadingUpSteamNodeInfo: false,
      upSteamNodeInfo: [],
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
      serverInfoFrom: {
        serverName: '',
        deployType: '',
        hostPath: ''
      },
      serverInfoFromValidate: {
        serverName: [
          {
            required: true,
            message: '服务名称不能为空',
            trigger: 'blur'
          }
        ],
        deployType: [
          {
            required: true,
            message: '部署类型不能为空',
            trigger: 'change'
          }
        ],
        hostPath: [
          {
            required: true,
            message: '应用节点不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  components: {
    upSteamName
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
    handleReset(name) {
      this.$refs[name].resetFields()
    },
    commitData() {
      this.utils.netUtil.post(this.$store,this.API_PTAH.serverInfoAdd, this.serverInfoFrom, () => {
        this.$Message.success('提交成功!')
        this.$router.push('/server-info-list')
      })
    }
  }
}
</script>
