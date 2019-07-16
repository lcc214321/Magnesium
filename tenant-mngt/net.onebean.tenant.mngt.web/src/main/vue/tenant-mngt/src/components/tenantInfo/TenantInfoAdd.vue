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
        <Form ref="tenantInfoFrom"
          :model="tenantInfoFrom"
          :rules="tenantInfoFromValidate"
          :label-width="80">
          <FormItem label="登录邮箱"
            prop="email">
            <i-input v-model="tenantInfoFrom.email"
              placeholder="请输入登录邮箱"></i-input>
          </FormItem>

          <FormItem label="商家名称"
            prop="tenantName">
            <i-input v-model="tenantInfoFrom.tenantName"
              placeholder="请输入商家名称"></i-input>
          </FormItem>

          <FormItem label="负责人"
            prop="principal">
            <i-input v-model="tenantInfoFrom.principal"
              placeholder="请输入负责人"></i-input>
          </FormItem>

          <FormItem label="手机号码"
            prop="mobile">
            <i-input v-model="tenantInfoFrom.mobile"
              placeholder="请输入手机号码"></i-input>
          </FormItem>

          <FormItem label="身份证号"
            prop="idCardNumber">
            <i-input v-model="tenantInfoFrom.idCardNumber"
              placeholder="请输入身份证号"></i-input>
          </FormItem>

          <FormItem label="企业账户"
            prop="corporateBankAccount">
            <i-input v-model="tenantInfoFrom.corporateBankAccount"
              placeholder="请输入企业账户"></i-input>
          </FormItem>

          <FormItem label="企业户名"
            prop="corporateAccountName">
            <i-input v-model="tenantInfoFrom.corporateAccountName"
              placeholder="请输入企业户名"></i-input>
          </FormItem>

          <FormItem label="银行类型"
            prop="bankType">
            <Select v-model="tenantInfoFrom.bankType"
              placeholder="选择银行类型">
              <Option v-for="item in bankTypeEunmArr"
                :value="item.value"
                :key="item.value">{{ item.label }}</Option>
            </Select>

          </FormItem>

          <FormItem label="同意保证金">
            <i-switch v-model="tenantInfoFrom.agreeGuaranteeCash"
              :true-value="this.trueValue"
              :false-value="this.falseValue"
              size="large">
              <span slot="open">是</span>
              <span slot="close">否</span>
            </i-switch>
          </FormItem>

          <FormItem label="保证金"
            v-show="tenantInfoFrom.agreeGuaranteeCash === this.trueValue"
            prop="guaranteeCash">
            <i-input v-model="tenantInfoFrom.guaranteeCash"
              placeholder="请输入保证金"></i-input>
          </FormItem>

          <FormItem label="所在市"
            prop="cityCode">
            <i-input v-model="tenantInfoFrom.cityCode"
              v-if="false"></i-input>
            <i-input v-model="tenantInfoFrom.cityCode"
              v-if="false"></i-input>
            <Cascader :data="initAreaSelectorData"
              :load-data="loadAreaData"
              @on-change="AreaDataHandleChange"
              placeholder="请选择所在市"></Cascader>
          </FormItem>

          <FormItem label="子公司"
            prop="isSubsidiaryCompany">
            <i-switch v-model="tenantInfoFrom.isSubsidiaryCompany"
              :true-value="this.trueValue"
              :false-value="this.falseValue"
              size="large">
              <span slot="open">是</span>
              <span slot="close">否</span>
            </i-switch>
          </FormItem>

          <FormItem label="租户状态"
            prop="status">
            <Select v-model="tenantInfoFrom.status"
              placeholder="选择应用类型   ">
              <Option v-for="item in tenantInfoStatusEunmArr"
                :value="item.value" :disabled="item.disabled"
                :key="item.value">{{ item.label }}</Option>
            </Select>
          </FormItem>

          <FormItem>
            <Button type="primary"
              @click="handleSubmit('tenantInfoFrom')">提交</Button>
            <Button @click="handleReset('tenantInfoFrom')"
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
      trueValue: '1',
      falseValue: '0',
      tenantInfoFrom: {
        email: '',
        tenantName: '',
        principal: '',
        mobile: '',
        idCardNumber: '',
        bankType: '',
        corporateBankAccount: '',
        corporateAccountName: '',
        agreeGuaranteeCash: '0',
        guaranteeCash: '',
        provinceCode: '',
        cityCode: '',
        isSubsidiaryCompany: '0',
        status: ''
      },
      tenantInfoStatusEunmArr: [
        {
          value: '0',
          label: '注册'
        },
        {
          value: '1',
          label: '使用',
          disabled: true
        },
        {
          value: '2',
          label: '冻结',
          disabled: true
        },
        {
          value: '3',
          label: '注销',
          disabled: true
        }
      ],
      bankTypeEunmArr: [
        {
          value: '0',
          label: '银联'
        },
        {
          value: '1',
          label: '支付宝'
        },
        {
          value: '2',
          label: '微信'
        }
      ],
      initAreaSelectorData: [
        {
          value: 'china',
          label: '中华人民共和国',
          children: [],
          type: 'country',
          loading: false
        }
      ],
      tenantInfoFromValidate: {
        email: [
          {
            required: true,
            type: 'email',
            message: '请填写正确的邮箱地址,最多32个字符',
            trigger: 'blur'
          }
        ],
        tenantName: [
          {
            required: true,
            message: '商家名称不能为空,最多10个字符',
            max: 10,
            trigger: 'blur'
          }
        ],
        principal: [
          {
            required: true,
            message: '负责人不能为空,最多20个字符',
            max: 20,
            trigger: 'blur'
          }
        ],
        mobile: [
          {
            required: true,
            message: '请填写正确的手机号码',
            min: 11,
            max: 11,
            trigger: 'blur'
          }
        ],
        idCardNumber: [
          {
            required: true,
            message: '请填写正确的身份证号',
            min: 18,
            max: 18,
            trigger: 'blur'
          }
        ],
        corporateBankAccount: [
          {
            required: true,
            message: '企业账户不能为空,最多20个字符',
            max: 20,
            trigger: 'blur'
          }
        ],
        corporateAccountName: [
          {
            required: true,
            message: '企业户名不能为空,最多20个字符',
            max: 20,
            trigger: 'blur'
          }
        ],
        bankType: [
          {
            required: true,
            message: '银行类型不能为空',
            trigger: 'change'
          }
        ],
        agreeGuaranteeCash: [
          {
            required: true,
            message: '请选择是否同意保证金',
            trigger: 'change'
          }
        ],
        guaranteeCash: [
          {
            required: true,
            pattern: /^[0-9]+([.]{1}[0-9]{1,2})?$/,
            message: '请输入保证金金额,精确到小数点后两位',
            trigger: 'blur'
          }
        ],
        provinceCode: [
          {
            required: true,
            message: '请选择所在省',
            trigger: 'change'
          }
        ],
        cityCode: [
          {
            required: true,
            message: '请选择所在市',
            trigger: 'change'
          }
        ],
        isSubsidiaryCompany: [
          {
            required: true,
            message: '请选择是否为子公司',
            trigger: 'change'
          }
        ],
        status: [
          {
            required: true,
            message: '请选择账户状态',
            trigger: 'change'
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
    loadAreaData(item, callback) {
      let apiPath = ''
      item.loading = true
      if (item.type === 'country') {
        apiPath = this.API_PTAH.provinceInfoFindAllForCascader
      }

      if (item.type === 'province') {
        apiPath = this.API_PTAH.cityInfoFindAllForCascader
      }

      this.utils.netUtil.post(this.$store,apiPath, { provinceCode: item.value }, response => {
        item.children = response.data.datas
        item.loading = false
        callback()
      })
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
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.tenantInfoAdd,
        this.tenantInfoFrom,
        response => {
          response.data
          this.$Message.success('提交成功!')
          this.$router.push('/tenant-info-list')
        }
      )
    },
    AreaDataHandleChange(value, selectedData) {
      let city = selectedData.pop()
      let province = selectedData.pop()
      if (typeof city != 'undefined' && city.type === 'city') {
        this.tenantInfoFrom.cityCode = city.value
      }
      if (typeof province != 'undefined' && province.type === 'province') {
        this.tenantInfoFrom.provinceCode = province.value
      }
    }
  }
}
</script>
