package net.onebean.tenant.mngt.vo;

import org.hibernate.validator.constraints.Range;

/**
* @author 0neBean
* @description 城市 model
* @date 2019-01-11 20:55:32
*/
public class FIndListTenantCityReq {

        @Range(message = "provinceCode can not be empty")
        private Integer provinceCode;
        public Integer getProvinceCode(){
            return this.provinceCode;
        }
        public void setProvinceCode(Integer provinceCode){
            this.provinceCode = provinceCode;
        }

}