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
        <Form ref="appInfoFrom"
          :model="appInfoFrom"
          :rules="fromValidate"
          :label-width="100">
          <FormItem label="应用名称"
            prop='appName'>
            <i-input v-model="appInfoFrom.appName"
              placeholder="请输入应用名称"></i-input>
          </FormItem>

          <FormItem label="应用类型"
            prop="appCategory">
            <Select v-model="appInfoFrom.appCategory"
              placeholder="选择应用类型   ">
              <Option v-for="item in appCategoryEunmArr"
                :value="item.value"
                :disabled="item.disabled"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem label="应用状态"
            prop='appStatus'>
            <Select v-model="appInfoFrom.appStatus"
              placeholder="请输入应用状态">
              <Option v-for="item in appStatusEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem label="API授权模式"
            prop='authType'
            v-if="notShowOnBasicApp()">
            <Select v-model="appInfoFrom.authType"
              placeholder="请输入API授权模式"
              disabled>
              <Option v-for="item in authTypeEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
            <ipWhiteList v-show="showOnInnerApi()" />
            <unLoginAccessWhiteList :app-id="appInfoFrom.id"
              v-show="showOnPrivateTokenAuthType()" />
          </FormItem>

          <FormItem label="账号授权模式"
            prop="loginType"
            v-if="showOnPrivateTokenAuthType() && notShowOnBasicApp()">
            <Select v-model="appInfoFrom.loginType"
              placeholder="选择账号授权模式">
              <Option v-for="item in loginTypeEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem label="SSO回调地址"
            prop="oauthBaseUrl"
            v-if="notShowOnBasicApp() && showOnSso()">
            <i-input v-model="appInfoFrom.oauthBaseUrl"
              placeholder="请输入SSO登陆成功回调地址"></i-input>
          </FormItem>

          <FormItem label="应用唯一ID"
            prop='openId'
            v-if="notShowOnBasicApp()">
            <i-input v-model="appInfoFrom.openId"
              disabled
              placeholder="请输入创建时间"></i-input>
          </FormItem>

          <FormItem label="应用秘钥"
            prop='secret'
            v-show="showOnOpenApi()">
            <i-input v-model="appInfoFrom.secret"
              disabled
              placeholder="请输入应用秘钥"></i-input>
          </FormItem>

          <FormItem label="访问令牌"
            prop='innerApiToken'
            v-show="showOnInnerApi()">
            <i-input v-model="appInfoFrom.innerApiToken"
              disabled
              placeholder="请输入访问令牌"></i-input>
          </FormItem>

          <FormItem label="PC端地址链接"
            prop="pcPageUrl"
            v-if="showOnBasicApp()">
            <i-input v-model="appInfoFrom.pcPageUrl"
              placeholder="请输入PC端地址链接"></i-input>
          </FormItem>

          <FormItem label="PC端图标icon"
            prop="pcMenuIcon"
            v-if="showOnBasicApp()">
            <i-input v-model="appInfoFrom.pcMenuIcon"
              placeholder="请输入PC端图标icon"></i-input>
          </FormItem>

          <FormItem label="PC端菜单排序"
            prop="menuSort"
            v-if="showOnBasicApp()">
            <i-input v-model="appInfoFrom.menuSort"
              placeholder="请输入PC端图标icon"></i-input>
          </FormItem>

          <FormItem label="创建时间"
            prop='createTime'>
            <i-input v-model="appInfoFrom.createTime"
              disabled
              placeholder="请输入创建时间"></i-input>
          </FormItem>

          <FormItem label="	修改时间"
            prop='updateTime'>
            <i-input v-model="appInfoFrom.updateTime"
              disabled
              placeholder="请输入修改时间"></i-input>
          </FormItem>

          <FormItem label="	操作人"
            prop='operatorName'>
            <i-input v-model="appInfoFrom.operatorName"
              disabled
              placeholder="请输入操作人"></i-input>
          </FormItem>

          <FormItem>
            <Button type="primary"
              @click="handleSubmit('appInfoFrom')">提交</Button>
          </FormItem>
        </Form>
      </i-col>
    </Row>
  </div>

</template>


<script>
import ipWhiteList from '../ipWhiteList/ipWhiteList'
import unLoginAccessWhiteList from '../unLoginAccessWhiteList/unLoginAccessWhiteList'

export default {
  components: {
    ipWhiteList,
    unLoginAccessWhiteList
  },
  props: {
    query: { userId: 0 }
  },
  data() {
    return {
      routerPath: this.$route.path,
      loginTypeEunmArr: [
        {
          value: '0',
          label: 'API (短信验证码登录注册/短信验证码登录/密码登录)'
        },
        {
          value: '1',
          label: 'SSO (短信验证码登录)'
        },
        {
          value: '2',
          label: 'SSO (密码登录)'
        }
      ],
      appCategoryEunmArr: [
        {
          value: '0',
          label: '基础应用'
        },
        {
          value: '1',
          label: '普通应用'
        }
      ],
      authTypeEunmArr: [
        {
          value: '0',
          label: '授权码 (共享令牌)'
        },
        {
          value: '1',
          label: '私有令牌  (一设备一令牌 需要登录)'
        },
        {
          value: '2',
          label: 'IP白名单+通行令牌 (管理后台模式)'
        },
        {
          value: '3',
          label: '私有免登陆令牌 (一设备一令牌 免登陆)'
        }
      ],
      appStatusEunmArr: [
        {
          value: '0',
          label: '连接'
        },
        {
          value: '1',
          label: '断开'
        }
      ],
      appInfoFrom: {
        id: this.$route.params.id,
        createTime: '',
        updateTime: '',
        operatorName: '',
        appName: '',
        authType: '',
        appStatus: '',
        appCategory: '',
        pcPageUrl: '',
        pcMenuIcon: '',
        oauthBaseUrl: '',
        menuSort: 0,
        loginType: ''
      },
      fromValidate: {
        loginType: [
          {
            required: true,
            message: '登录模式不能为空',
            trigger: 'blur'
          }
        ],
        oauthBaseUrl: [
          {
            required: true,
            message: '请正确填写url格式',
            pattern: /^(http|ftp|https){1}[:]{1}[/]{2}([0-9a-zA-Z]|[-]|[_])+\S+$/,
            trigger: 'blur'
          }
        ],
        appName: [
          {
            required: true,
            message: '应用名不能为空',
            trigger: 'blur'
          }
        ],
        appCategory: [
          {
            required: true,
            message: '应用分类不能为空',
            trigger: 'change'
          }
        ],
        pcPageUrl: [
          {
            required: true,
            message: '请正确填写url格式',
            pattern: /^(http|ftp|https){1}[:]{1}[/]{2}([0-9a-zA-Z]|[-]|[_])+\S+$/,
            trigger: 'blur'
          }
        ],
        pcMenuIcon: [
          {
            required: true,
            message:
              '请填写标准的iview图标: https://www.iviewui.com/components/icon',
            pattern: /^\S+[-]{1}\S+$/,
            trigger: 'blur'
          }
        ],
        menuSort: [
          {
            required: true,
            message: 'PC菜单排序值只能为4位数以内的数字',
            pattern: /^[0-9]{1,4}$/,
            trigger: 'blur'
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
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.appInfoFindById,
        { data: this.appInfoFrom },
        response => {
          this.appInfoFrom = response.data.datas
        }
      )
    },
    commitData() {
      this.appInfoFrom.id = this.$route.params.id
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.appInfoUpdate,
        this.appInfoFrom,
        () => {
          this.$Message.success('提交成功!')
          this.$router.push('/app-info-list')
        }
      )
    },
    showOnOpenApi() {
      return this.appInfoFrom.authType === '3' ||
        this.appInfoFrom.authType === '1' ||
        this.appInfoFrom.authType === '0'
        ? true
        : false
    },
    showOnPrivateTokenAuthType() {
      return this.appInfoFrom.authType === '1' ? true : false
    },
    showOnInnerApi() {
      return this.appInfoFrom.authType === '2' ? true : false
    },
    showOnBasicApp() {
      return this.appInfoFrom.appCategory === '0' ? true : false
    },
    notShowOnBasicApp() {
      return !this.showOnBasicApp()
    },
    showOnSso() {
      return this.appInfoFrom.loginType === '1' || this.appInfoFrom.loginType === '2'? true : false
    }
  }
}
</script>
