package net.onebean.tenant.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
public class AddTtenantCityReq {

    @NotEmpty(message = "cityName can not be empty")
    private String cityName;
    public String getCityName(){
        return this.cityName;
    }
    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    @Range(message = "citySort can not be empty")
    private Integer citySort;
    public Integer getCitySort(){
        return this.citySort;
    }
    public void setCitySort(Integer citySort){
        this.citySort = citySort;
    }

    @Range(message = "level can not be empty")
    private Integer level;
    public Integer getLevel(){
        return this.level;
    }
    public void setLevel(Integer level){
        this.level = level;
    }

    @NotEmpty(message = "provinceCode can not be empty")
    private String provinceCode;
    public String getProvinceCode() {
        return provinceCode;
    }
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }


    private Integer operatorId;
    public Integer getOperatorId(){
        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId){
        this.operatorId = operatorId;
    }


    private String operatorName;
    public String getOperatorName(){
        return this.operatorName;
    }
    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }

}