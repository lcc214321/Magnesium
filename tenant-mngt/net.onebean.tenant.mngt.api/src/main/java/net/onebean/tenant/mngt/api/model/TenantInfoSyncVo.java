package net.onebean.tenant.mngt.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
* @author 0neBean
* @description 租户信息 model
* @date 2019-01-11 20:56:12
*/
public class TenantInfoSyncVo implements Serializable {

        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }


        /**
         * 登录邮箱号
         */
        private String email;
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
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }


        private String isDeleted;
        public String getIsDeleted() {
            return isDeleted;
        }
        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }
}