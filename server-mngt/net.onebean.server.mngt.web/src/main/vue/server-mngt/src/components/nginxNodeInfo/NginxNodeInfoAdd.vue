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
        <Form ref="nginxNodeInfoFrom"
          :model="nginxNodeInfoFrom"
          :rules="nginxNodeInfoFromValidate"
          :label-width="150">

          <FormItem label="节点内网IP地址"
            prop="ipAddress">
            <i-input v-model="nginxNodeInfoFrom.ipAddress"
              placeholder="请输入节点内网IP地址"></i-input>
          </FormItem>

          <FormItem label="所在服务器访问端口号"
            prop="accessPort">
            <i-input v-model="nginxNodeInfoFrom.accessPort"
              placeholder="请输入访问端口号"></i-input>
          </FormItem>

          <FormItem label="访问账户"
            prop="accessUser">
            <i-input v-model="nginxNodeInfoFrom.accessUser"
              placeholder="请输入访问账户"></i-input>
          </FormItem>

          <FormItem label="访问授权方式"
            prop="accessAuthType">
            <Select v-model="nginxNodeInfoFrom.accessAuthType"
              placeholder="选择访问授权方式">
              <Option v-for="item in accessAuthTypeEunmArr"
                :value="item.value"
                :disabled="item.disabled"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem label="访问密码"
            :rules="accessPasswordValidateRules()"
            v-show="showOnPassword()"
            prop="accessPassword">
            <i-input v-model="nginxNodeInfoFrom.accessPassword"
              :disabled="nginxNodeInfoFrom.accessAuthType != accessAuthTypeEunmArr[0].value"
              placeholder="请输入访问密码"></i-input>
          </FormItem>

          <FormItem label="私钥相对地址"
            :rules="accessRsaPathValidateRules()"
            v-show="showOnRsa()"
            prop="accessRsaPath">
            <i-input v-model="nginxNodeInfoFrom.accessRsaPath"
              :disabled="nginxNodeInfoFrom.accessAuthType != accessAuthTypeEunmArr[1].value"
              placeholder="私钥相对地址"></i-input>
          </FormItem>

          <FormItem label="配置文件路径"
            prop="confPath">
            <i-input v-model="nginxNodeInfoFrom.confPath"
              placeholder="请输入配置文件路径"></i-input>
          </FormItem>

          <FormItem>
            <Button type="primary"
              @click="handleSubmit('nginxNodeInfoFrom')">提交</Button>
            <Button @click="handleReset('nginxNodeInfoFrom')"
              style="margin-left: 8px">重置</Button>
          </FormItem>
        </Form>
      </i-col>
    </Row>
  </div>

</template>


<script>
export default {
  data() {
    return {
      routerPath: this.$route.path,
      accessAuthTypeEunmArr: [
        {
          value: '0',
          label: '密码模式'
        },
        {
          value: '1',
          label: '公私钥模式'
        }
      ],
      nginxNodeInfoFrom: {
        serverId: '',
        apiName: ''
      },
      nginxNodeInfoFromValidate: {
        ipAddress: [
          {
            required: true,
            pattern: /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
            message: 'ip地址格式输入不正确',
            trigger: 'blur'
          }
        ],
        accessPort: [
          {
            required: true,
            pattern: /^[0-9]{4,}$/,
            message: '所在服务器访问端口号输入不正确',
            trigger: 'blur'
          }
        ],
        accessUser: [
          {
            required: true,
            max: 20,
            pattern: /^[a-zA-Z]+$/,
            message: '访问账户输入不能为空,只能为英文,最大20个字符',
            trigger: 'blur'
          }
        ],
        accessAuthType: [
          {
            required: true,
            message: '访问授权方式不能为空',
            trigger: 'change'
          }
        ],
        accessPassword: [
          {
            required: true,
            max: 20,
            mix: 5,
            message: '访问账户输入不能为空,最小5个字符,最大20个字符',
            trigger: 'blur'
          }
        ],
        accessRsaPath: [
          {
            required: true,
            pattern: /^\/\S*[A-Za-z0-9]+$/,
            message: '私钥相对地址不能为空,并且只能以`/`开头 英文或数字结尾',
            trigger: 'blur'
          }
        ],
        confPath: [
          {
            pattern: /^\/\S*[A-Za-z0-9]+$/,
            required: true,
            message: '配置文件路径不能为空 并且只能以`/`开头 英文或数字结尾',
            trigger: 'blur'
          }
        ]
      }
    }
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
    handleReset(name) {
      this.$refs[name].resetFields()
    },
    commitData() {
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.nginxNodeInfoAdd,
        this.nginxNodeInfoFrom,
        () => {
          this.$Message.success('提交成功!')
          this.$router.push('/nginx-node-info-list')
        }
      )
    },
    accessPasswordValidateRules() {
      let tempRuls = this.nginxNodeInfoFromValidate.accessPassword
      let required =
        this.nginxNodeInfoFrom.accessAuthType !=
        this.accessAuthTypeEunmArr[1].value
      tempRuls[0].required = required
      return tempRuls
    },
    accessRsaPathValidateRules() {
      let tempRuls = this.nginxNodeInfoFromValidate.accessRsaPath
      let required =
        this.nginxNodeInfoFrom.accessAuthType !=
        this.accessAuthTypeEunmArr[0].value
      tempRuls[0].required = required
      return tempRuls
    },
    showOnPassword() {
      return this.nginxNodeInfoFrom.accessAuthType == this.accessAuthTypeEunmArr[0].value
    },
    showOnRsa() {
      return this.nginxNodeInfoFrom.accessAuthType == this.accessAuthTypeEunmArr[1].value
    }
  }
}
</script>
