<style scoped>
.title {
  font-size: 16px;
  color: #464c5b;
  font-weight: bold;
}
.login-card {
  margin-top: 120px;
}
</style>

<template>
  <Row>
    <i-col span="4"
      offset="10">
      <Card style="width:350px"
        class="login-card">
        <span class="title">&nbsp;&nbsp;登录账号</span>
        <Divider />
        <Form label-position="top"
          ref="passwordLoginFrom"
          :model="passwordLoginFrom"
          :rules="passwordLoginFromValidate">

          <FormItem label="用户名"
            prop="telPhone">
            <i-input type="text"
              v-model="passwordLoginFrom.telPhone"
              placeholder="请输入用户名">
              <Icon type="ios-person-outline"
                slot="prepend"></Icon>
            </i-input>
          </FormItem>

          <FormItem label="密码"
            prop="password">
            <i-input type="password"
              v-model="passwordLoginFrom.password"
              placeholder="请输入密码">
              <Icon type="ios-lock-outline"
                slot="prepend"></Icon>
            </i-input>
          </FormItem>

          <FormItem>
            <Button type="success"
              :disabled="commitButtonDisabled"
              long
              @click="handleSubmit('passwordLoginFrom')">账号密码登录</Button>
          </FormItem>
        </Form>

      </Card>
    </i-col>
  </Row>
</template>

<script>
export default {
  name: 'SmsCodeLoginCard',
  data() {
    return {
      commitButtonDisabled: false,
      uagAppId: this.$route.query.uagAppId,
      uagDeviceToken: this.$route.query.uagDeviceToken,
      uagAccessToken: this.$route.query.uagAccessToken,
      passwordLoginFrom: {
        telPhone: '',
        password: ''
      },
      passwordLoginFromValidate: {
        telPhone: [
          {
            pattern: /^[0-9]{11}$/,
            required: true,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            type: 'string',
            min: 5,
            max: 30,
            message: '密码为长度为5-30之间的字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted: function() {
    this.checkSsoParam()
  },
  methods: {
    checkSsoParam: function() {
      if (
        typeof this.uagAppId === 'undefined' ||
        typeof this.uagAccessToken === 'undefined'||
        typeof this.uagDeviceToken === 'undefined' 
      ) {
        this.$router.push('/err')
      }
    },
    doLogin: function() {
      this.commitButtonDisabled = 'disabled'
      this.utils.netUtil.postWithAccessToken(
        this.uagAppId,
        this.uagAccessToken,
        this.API_PTAH.authPasswordLogin,
        {
          telPhone: this.passwordLoginFrom.telPhone,
          password: this.passwordLoginFrom.password
        },
        resp => {
          if (resp.data.datas.loginStatus === '1') {
            const oauthBaseUrl = resp.data.datas.oauthBaseUrl
            window.location.href = `${oauthBaseUrl}?uagDeviceToken=${this.uagDeviceToken}`
          }
        },
        () => {
          this.commitButtonDisabled = false
        }
      )
    },
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.doLogin()
        } else {
          this.$Message.error('请完善表单信息!')
        }
      })
    }
  }
}
</script>