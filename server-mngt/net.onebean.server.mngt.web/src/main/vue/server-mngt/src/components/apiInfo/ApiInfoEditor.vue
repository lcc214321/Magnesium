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
        <Form ref="apiInfoFrom"
          :model="apiInfoFrom"
          :rules="apiInfoFromValidate"
          :label-width="80">

          <FormItem label="接口名称"
            prop="apiName">
            <i-input v-model="apiInfoFrom.apiName"
              placeholder="请输入接口名称"></i-input>
          </FormItem>

          <FormItem label="接口地址"
            prop="apiUri">
            <i-input v-model="apiInfoFrom.apiUri"
              placeholder="请输入接口地址"></i-input>
          </FormItem>

          <FormItem label="代理地址"
            prop="proxyPath">
            <i-input v-model="apiInfoFrom.proxyPath"
              placeholder="请输入代理地址"></i-input>
          </FormItem>

          <FormItem label="接口状态"
            prop="apiStatus">
            <Select v-model="apiInfoFrom.apiStatus"
              placeholder="选择接口状态">
              <Option v-for="item in apiStatusEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem>
            <Button type="primary"
              @click="handleSubmit('apiInfoFrom')">提交</Button>
          </FormItem>
        </Form>
      </i-col>
    </Row>
  </div>

</template>


<script>


export default {
  props: {
    query: { userId: 0 }
  },
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
      apiInfoFrom: {
        id: this.$route.params.id,
        serverId: this.$route.params.sid,
        apiName: '',
        apiUri: '',
        proxyPath: '',
        apiStatus: ''
      },
      apiInfoFromValidate: {
        apiName: [
          {
            required: true,
            message: '接口名称不能为空',
            trigger: 'blur'
          }
        ],
        apiUri: [
          {
            pattern: /^\/\S*[A-Za-z0-9]+$/,
            required: true,
            message: '接口地址不能为空 并且只能以`/`开头 英文或数字结尾',
            trigger: 'blur'
          }
        ],
        proxyPath: [
          {
            pattern: /^\/\S*[A-Za-z0-9]+$/,
            required: true,
            message: '代理地址不能为空 并且只能以`/`开头 英文或数字结尾',
            trigger: 'blur'
          }
        ],
        apiStatus: [
          {
            required: true,
            message: '接口状态不能为空',
            trigger: 'change'
          }
        ]
      }
    }
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
      this.utils.netUtil.post(this.$store,this.API_PTAH.apiInfoFindById, this.apiInfoFrom, response => {
        this.apiInfoFrom = response.data.datas
      })
    },
    commitData() {
      this.apiInfoFrom.id = this.$route.params.id
      this.utils.netUtil.post(this.$store,this.API_PTAH.apiInfoUpdate, this.apiInfoFrom, () => {
        this.$Message.success('提交成功!')
        this.$router.push(
          `/server-info-list/api-info-list/${this.$route.params.sid}`
        )
      })
    }
  }
}
</script>
