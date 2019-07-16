package net.onebean.tenant.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
* @author 0neBean
* @description 城市 model
* @date 2019-01-11 20:55:32
*/
public class FIndTenantCityByNameReq {

        @NotEmpty(message = "cityName can not be empty")
        private String cityName;
        public String getCityName(){
            return this.cityName;
        }
        public void setCityName(String cityName){
        this.cityName = cityName;
    }

}