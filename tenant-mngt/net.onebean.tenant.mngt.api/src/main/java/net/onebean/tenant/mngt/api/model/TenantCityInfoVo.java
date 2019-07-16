package net.onebean.tenant.mngt.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author 0neBean
* @description 租户信息 model
* @date 2019-01-11 20:56:12
*/
public class TenantCityInfoVo implements Serializable {

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


        private Integer province;

        public Integer getProvince(){
            return this.province;
        }
        public void setProvince(Integer province){
            this.province = province;
        }


        private Integer city;

        public Integer getCity(){
                return this.city;
            }
        public void setCity(Integer city){
            this.city = city;
        }


        private String isSubsidiaryCompany;
        public String getIsSubsidiaryCompany(){
            return this.isSubsidiaryCompany;
        }
        public void setIsSubsidiaryCompany(String isSubsidiaryCompany){
            this.isSubsidiaryCompany = isSubsidiaryCompany;
        }

        private String cityName;
        private String provinceName;

        public String getCityName() {
            return cityName;
        }
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
        public String getProvinceName() {
            return provinceName;
        }
        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }
}