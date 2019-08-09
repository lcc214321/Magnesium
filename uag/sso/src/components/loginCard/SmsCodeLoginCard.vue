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
          ref="smsLoginFrom"
          :model="smsLoginFrom"
          :rules="smsLoginFromValidate">

          <FormItem label="用户名"
            prop="telPhone">
            <i-input type="text"
              v-model="smsLoginFrom.telPhone"
              placeholder="请输入手机号">
              <Icon type="ios-person-outline"
                slot="prepend"></Icon>
            </i-input>
          </FormItem>

          <Row>
            <i-col span="14">
              <FormItem prop="smsCode"
                label="验证码">
                <i-input type="text"
                  v-model="smsLoginFrom.smsCode"
                  placeholder="请输入短信验证码">
                  <Icon type="ios-lock-outline"
                    slot="prepend"></Icon>
                </i-input>
              </FormItem>
            </i-col>

            <i-col span="2"
              style="text-align: center">&emsp;</i-col>

            <i-col span="8">
              <FormItem>
                <CountDownButton :countDown="countDown"
                  :showText="showText"
                  ref="countDownButton"
                  :reShowText="reShowText"
                  @click.native="sendSmsCode" />
              </FormItem>
            </i-col>

          </Row>

          <FormItem>
            <Button type="success"
              long
              @click="handleSubmit('smsLoginFrom')">短信验证码登录</Button>
          </FormItem>
        </Form>

      </Card>
    </i-col>
  </Row>
</template>

<script>
import CountDownButton from '../button/CountDownButton'
export default {
  name: 'SmsCodeLoginCard',
  components: {
    CountDownButton
  },
  data() {
    return {
      countDown: 60,
      showText: '获取验证码',
      reShowText: '重新获取',
      uagAppId: this.$route.query.uagAppId,
      uagDeviceToken: this.$route.query.uagDeviceToken,
      uagAccessToken: this.$route.query.uagAccessToken,
      smsLoginFrom: {
        telPhone: '',
        smsCode: ''
      },
      smsLoginFromValidate: {
        telPhone: [
          {
            pattern: /^[0-9]{11}$/,
            required: true,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ],
        smsCode: [
          {
            pattern: /^[0-9]{4}$/,
            required: true,
            message: '请输入正确格式的验证码',
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
    sendSmsCode() {
      const pattern = /^[0-9]{11}$/
      const telPhone = this.smsLoginFrom.telPhone
      if (!pattern.test(telPhone)) {
        this.$Message.error('请输入正确的手机号码!')
        return
      }
      this.utils.netUtil.postWithAccessToken(
        this.uagAppId,
        this.uagAccessToken,
        this.API_PTAH.authSendLoginSms,
        { deviceToken: this.deviceToken, telPhone: telPhone },
        resp => {
          if (resp.data.datas) {
            this.$Message.success('已发送短信验证码')
            this.$refs.countDownButton.changeCountNumber()
          }
        }
      )
    },
    checkSsoParam: function() {
      if (
        typeof this.uagAppId === 'undefined' ||
        typeof this.uagAccessToken === 'undefined' ||
        typeof this.uagDeviceToken === 'undefined' 
      ) {
        this.$router.push('/err')
      }
    },
    doLogin: function() {
      this.utils.netUtil.postWithAccessToken(
        this.uagAppId,
        this.uagAccessToken,
        this.API_PTAH.authSmsCodeLogin,
        {
          telPhone: this.smsLoginFrom.telPhone,
          smsCode: this.smsLoginFrom.smsCode
        },
        resp => {
          if (resp.data.datas.loginStatus === '1') {
            let oauthBaseUrl = resp.data.datas.oauthBaseUrl
            oauthBaseUrl = `${oauthBaseUrl}?uagDeviceToken=${this.uagDeviceToken}`
            window.location.href = oauthBaseUrl
          }
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