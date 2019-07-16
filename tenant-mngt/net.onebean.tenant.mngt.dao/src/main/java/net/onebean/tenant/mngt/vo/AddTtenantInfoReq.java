package net.onebean.tenant.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
* @author 0neBean
* @description 租户信息 model
* @date 2019-01-11 20:56:12
*/
public class AddTtenantInfoReq {

        @NotEmpty(message = "email can not be empty")
        private String email;
        public String getEmail(){
            return this.email;
        }
        public void setEmail(String email){
            this.email = email;
        }

        @NotEmpty(message = "tenantName can not be empty")
        private String tenantName;
        public String getTenantName(){
            return this.tenantName;
        }
        public void setTenantName(String tenantName){
            this.tenantName = tenantName;
        }

        @NotEmpty(message = "principal can not be empty")
        private String principal;
        public String getPrincipal(){
            return this.principal;
        }
        public void setPrincipal(String principal){
            this.principal = principal;
        }

        @NotEmpty(message = "mobile can not be empty")
        private String mobile;
        public String getMobile(){
            return this.mobile;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }

        @NotEmpty(message = "idCardNumber can not be empty")
        private String idCardNumber;
        public String getIdCardNumber(){
            return this.idCardNumber;
        }
        public void setIdCardNumber(String idCardNumber){
            this.idCardNumber = idCardNumber;
        }

        @NotEmpty(message = "corporateBankAccount can not be empty")
        private String corporateBankAccount;
        public String getCorporateBankAccount(){
            return this.corporateBankAccount;
        }
        public void setCorporateBankAccount(String corporateBankAccount){
            this.corporateBankAccount = corporateBankAccount;
        }

        @NotEmpty(message = "corporateAccountName can not be empty")
        private String corporateAccountName;
        public String getCorporateAccountName(){
            return this.corporateAccountName;
        }
        public void setCorporateAccountName(String corporateAccountName){
            this.corporateAccountName = corporateAccountName;
        }

        @NotEmpty(message = "bankType can not be empty")
        private String bankType;
        public String getBankType(){
            return this.bankType;
        }
        public void setBankType(String bankType){
            this.bankType = bankType;
        }

        @NotEmpty(message = "agreeGuaranteeCash can not be empty")
        private String agreeGuaranteeCash;
        public String getAgreeGuaranteeCash(){
            return this.agreeGuaranteeCash;
        }
        public void setAgreeGuaranteeCash(String agreeGuaranteeCash){
            this.agreeGuaranteeCash = agreeGuaranteeCash;
        }

        private String guaranteeCash;
        public String getGuaranteeCash(){
            return this.guaranteeCash;
        }
        public void setGuaranteeCash(String guaranteeCash){
            this.guaranteeCash = guaranteeCash;
        }

        @NotNull(message = "provinceCode can not be empty")
        private Integer provinceCode;
        public Integer getProvinceCode(){
            return this.provinceCode;
        }
        public void setProvinceCode(Integer provinceCode){
            this.provinceCode = provinceCode;
        }

        @NotNull(message = "cityCode can not be empty")
        private Integer cityCode;
        public Integer getCityCode(){
            return this.cityCode;
        }
        public void setCityCode(Integer cityCode){
            this.cityCode = cityCode;
        }

        @NotEmpty(message = "isSubsidiaryCompany can not be empty")
        private String isSubsidiaryCompany;
        public String getIsSubsidiaryCompany(){
            return this.isSubsidiaryCompany;
        }
        public void setIsSubsidiaryCompany(String isSubsidiaryCompany){
            this.isSubsidiaryCompany = isSubsidiaryCompany;
        }

        @NotEmpty(message = "status can not be empty")
        private String status;
        public String getStatus(){
            return this.status;
        }
        public void setStatus(String status){
            this.status = status;
        }


}