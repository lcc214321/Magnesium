package net.onebean.tenant.mngt.vo;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
public class FindTenantCityReq {

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


    private String cityName;
    public String getCityName(){
        return this.cityName;
    }
    public void setCityName(String cityName){
        this.cityName = cityName;
    }

}