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
        <Form ref="provinceInfoFrom" :model="provinceInfoFrom" :rules="provinceInfoFromValidate" :label-width="80">
          <FormItem label="省份名称"
            prop="provinceName">
            <i-input v-model="provinceInfoFrom.provinceName"
              placeholder="请输入省份名称"></i-input>
          </FormItem>

          <FormItem label="省份编码"
            prop="provinceCode">
            <i-input v-model="provinceInfoFrom.provinceCode"
              placeholder="请输入省份编码"></i-input>
          </FormItem>

          <FormItem label="排序值"
            prop="proSort">
            <i-input v-model="provinceInfoFrom.proSort"
              placeholder="请输入排序值"></i-input>
          </FormItem>

          <FormItem>
            <Button type="primary"
              @click="commitData()">提交</Button>
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
      provinceInfoFrom: {
          id: this.$route.params.id,
          createTime: '',
          updateTime: '',
          operatorName: '',
          appName: '',
          appCategory: ''
      },
      provinceInfoFromValidate: {
        provinceName: [
          {
            required: true,
            message: '请填写省份名称,最多10个字符',
            max: 10,
            trigger: 'blur'
          }
        ],
        provinceCode: [
          {
            required: true,
            message: '请填写省份编码,最多10个字符',
            max: 10,
            trigger: 'blur'
          }
        ],
        proSort: [
          {
            required: true,
            message: '请填写省份编码,最多10位',
            max: 10,
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
        this.API_PTAH.provinceInfoFindById,this.provinceInfoFrom,
        response => { 
          this.provinceInfoFrom = response.data.datas
        }
      )
    },
    commitData() {
      this.provinceInfoFrom.id = this.$route.params.id
      this.utils.netUtil.post(this.$store,
        this.API_PTAH.provinceInfoUpdate,this.provinceInfoFrom,
        response => { 
          response.data
          this.$Message.success('提交成功!')
          this.$router.push('/province-info-list')
        }
      )
    }
  }
}
</script>
