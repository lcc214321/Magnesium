package net.onebean.tenant.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
* @author 0neBean
* @description 租户信息 model
* @date 2019-01-11 20:56:12
*/
public class ModifyTtenantInfoReq {

        @NotEmpty(message = "id can not be empty")
        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        private String email;
        public String getEmail(){
            return this.email;
        }
        public void setEmail(String email){
            this.email = email;
        }


        private String tenantName;
        public String getTenantName(){
            return this.tenantName;
        }
        public void setTenantName(String tenantName){
            this.tenantName = tenantName;
        }


        private String principal;
        public String getPrincipal(){
            return this.principal;
        }
        public void setPrincipal(String principal){
            this.principal = principal;
        }


        private String mobile;
        public String getMobile(){
            return this.mobile;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }


        private String idCardNumber;
        public String getIdCardNumber(){
            return this.idCardNumber;
        }
        public void setIdCardNumber(String idCardNumber){
            this.idCardNumber = idCardNumber;
        }


        private String corporateBankAccount;
        public String getCorporateBankAccount(){
            return this.corporateBankAccount;
        }
        public void setCorporateBankAccount(String corporateBankAccount){
            this.corporateBankAccount = corporateBankAccount;
        }


        private String corporateAccountName;
        public String getCorporateAccountName(){
            return this.corporateAccountName;
        }
        public void setCorporateAccountName(String corporateAccountName){
            this.corporateAccountName = corporateAccountName;
        }


        private String bankType;
        public String getBankType(){
            return this.bankType;
        }
        public void setBankType(String bankType){
            this.bankType = bankType;
        }


        private String agreeGuaranteeCash;
        public String getAgreeGuaranteeCash(){
            return this.agreeGuaranteeCash;
        }
        public void setAgreeGuaranteeCash(String agreeGuaranteeCash){
            this.agreeGuaranteeCash = agreeGuaranteeCash;
        }


        private BigDecimal guaranteeCash;
        public BigDecimal getGuaranteeCash(){
            return this.guaranteeCash;
        }
        public void setGuaranteeCash(BigDecimal guaranteeCash){
            this.guaranteeCash = guaranteeCash;
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
         * 所在省
         */
        private Integer provinceCode;
        public Integer getProvinceCode(){
            return this.provinceCode;
        }
        public void setProvinceCode(Integer provinceCode){
            this.provinceCode = provinceCode;
        }


        private String isSubsidiaryCompany;
        public String getIsSubsidiaryCompany(){
            return this.isSubsidiaryCompany;
        }
        public void setIsSubsidiaryCompany(String isSubsidiaryCompany){
            this.isSubsidiaryCompany = isSubsidiaryCompany;
        }

        private String status;
        public String getStatus(){
            return this.status;
        }
        public void setStatus(String status){
            this.status = status;
        }



}