package net.onebean.tenant.mngt.vo;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
public class TtenantCityVo {

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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


    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime = createTime;
    }


    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime = updateTime;
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