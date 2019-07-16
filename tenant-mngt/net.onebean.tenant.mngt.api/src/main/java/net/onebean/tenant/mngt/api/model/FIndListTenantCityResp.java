package net.onebean.tenant.mngt.api.model;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description 城市 model
* @date 2019-01-11 20:55:32
*/
public class FIndListTenantCityResp {

        private String cityName;
        public String getCityName(){
            return this.cityName;
        }
        public void setCityName(String cityName){
            this.cityName = cityName;
        }


        private Integer provinceId;
        public Integer getProvinceId(){
            return this.provinceId;
        }
        public void setProvinceId(Integer provinceId){
            this.provinceId = provinceId;
        }


        private Integer citySort;
        public Integer getCitySort(){
            return this.citySort;
        }
        public void setCitySort(Integer citySort){
            this.citySort = citySort;
        }


        private Integer level;
        public Integer getLevel(){
            return this.level;
        }
        public void setLevel(Integer level){
            this.level = level;
        }

        private String cityCode;
        public String getCityCode() {
            return cityCode;
        }
        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        private String provinceCode;
        public String getProvinceCode() {
            return provinceCode;
        }
        public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

}