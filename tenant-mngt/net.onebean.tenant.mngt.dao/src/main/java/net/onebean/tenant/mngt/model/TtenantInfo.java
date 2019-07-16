package net.onebean.tenant.mngt.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.math.BigDecimal;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description 租户信息 model
* @date 2019-02-25 15:38:56
*/
@TableName("t_tenant_info")
public class TtenantInfo extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * 登录邮箱号
        */
        private String email;
        @FiledName("email")
        public String getEmail(){
            return this.email;
        }
        public void setEmail(String email){
            this.email = email;
        }


        /**
        * 商家名称
        */
        private String tenantName;
        @FiledName("tenantName")
        public String getTenantName(){
            return this.tenantName;
        }
        public void setTenantName(String tenantName){
            this.tenantName = tenantName;
        }


        /**
        * 负责人
        */
        private String principal;
        @FiledName("principal")
        public String getPrincipal(){
            return this.principal;
        }
        public void setPrincipal(String principal){
            this.principal = principal;
        }


        /**
        * 手机号码
        */
        private String mobile;
        @FiledName("mobile")
        public String getMobile(){
            return this.mobile;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }


        /**
        * 身份证号
        */
        private String idCardNumber;
        @FiledName("idCardNumber")
        public String getIdCardNumber(){
            return this.idCardNumber;
        }
        public void setIdCardNumber(String idCardNumber){
            this.idCardNumber = idCardNumber;
        }


        /**
        * 企业银行账户
        */
        private String corporateBankAccount;
        @FiledName("corporateBankAccount")
        public String getCorporateBankAccount(){
            return this.corporateBankAccount;
        }
        public void setCorporateBankAccount(String corporateBankAccount){
            this.corporateBankAccount = corporateBankAccount;
        }


        /**
        * 企业账户名
        */
        private String corporateAccountName;
        @FiledName("corporateAccountName")
        public String getCorporateAccountName(){
            return this.corporateAccountName;
        }
        public void setCorporateAccountName(String corporateAccountName){
            this.corporateAccountName = corporateAccountName;
        }


        /**
        * 银行类型
        */
        private String bankType;
        @FiledName("bankType")
        public String getBankType(){
            return this.bankType;
        }
        public void setBankType(String bankType){
            this.bankType = bankType;
        }


        /**
        * 是否同意保证金,0否1是
        */
        private String agreeGuaranteeCash;
        @FiledName("agreeGuaranteeCash")
        public String getAgreeGuaranteeCash(){
            return this.agreeGuaranteeCash;
        }
        public void setAgreeGuaranteeCash(String agreeGuaranteeCash){
            this.agreeGuaranteeCash = agreeGuaranteeCash;
        }


        /**
        * 保证金
        */
        private BigDecimal guaranteeCash;
        @FiledName("guaranteeCash")
        public BigDecimal getGuaranteeCash(){
            return this.guaranteeCash;
        }
        public void setGuaranteeCash(BigDecimal guaranteeCash){
            this.guaranteeCash = guaranteeCash;
        }


        /**
        * 所在省
        */
        private Integer provinceCode;
        @FiledName("provinceCode")
        public Integer getProvinceCode(){
            return this.provinceCode;
        }
        public void setProvinceCode(Integer provinceCode){
            this.provinceCode = provinceCode;
        }


        /**
        * 所在市
        */
        private Integer cityCode;
        @FiledName("cityCode")
        public Integer getCityCode(){
            return this.cityCode;
        }
        public void setCityCode(Integer cityCode){
            this.cityCode = cityCode;
        }


        /**
        * 是否为子公司
        */
        private String isSubsidiaryCompany;
        @FiledName("isSubsidiaryCompany")
        public String getIsSubsidiaryCompany(){
            return this.isSubsidiaryCompany;
        }
        public void setIsSubsidiaryCompany(String isSubsidiaryCompany){
            this.isSubsidiaryCompany = isSubsidiaryCompany;
        }


        /**
        * 注册:0, 使用:1, 冻结:2, 注销:3
        */
        private String status;
        @FiledName("status")
        public String getStatus(){
            return this.status;
        }
        public void setStatus(String status){
            this.status = status;
        }


        /**
        * 创建时间
        */
        private Timestamp createTime;
        @FiledName("createTime")
        public Timestamp getCreateTime(){
            return this.createTime;
        }
        public void setCreateTime(Timestamp createTime){
            this.createTime = createTime;
        }


        /**
        * 更新时间
        */
        private Timestamp updateTime;
        @FiledName("updateTime")
        public Timestamp getUpdateTime(){
            return this.updateTime;
        }
        public void setUpdateTime(Timestamp updateTime){
            this.updateTime = updateTime;
        }


        /**
        * 操作人ID
        */
        private Integer operatorId;
        @FiledName("operatorId")
        public Integer getOperatorId(){
            return this.operatorId;
        }
        public void setOperatorId(Integer operatorId){
            this.operatorId = operatorId;
        }


        /**
        * 操作人姓名
        */
        private String operatorName;
        @FiledName("operatorName")
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }


        /**
        * 逻辑删除,0否1是
        */
        private String isDeleted;
        @FiledName("isDeleted")
        public String getIsDeleted(){
            return this.isDeleted;
        }
        public void setIsDeleted(String isDeleted){
            this.isDeleted = isDeleted;
        }


        private String isSync;
        @FiledName("isSync")
        public String getIsSync() {
            return isSync;
        }
        public void setIsSync(String isSync) {
            this.isSync = isSync;
        }
}