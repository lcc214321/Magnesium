package net.onebean.tenant.mngt.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description 城市 model
* @date 2019-01-11 20:55:32
*/
@TableName("t_tenant_city")
public class TtenantCity extends BaseModel implements InterfaceBaseDeletedModel {



        private String cityName;
        @FiledName("cityName")
        public String getCityName(){
            return this.cityName;
        }
        public void setCityName(String cityName){
            this.cityName = cityName;
        }


        private Integer citySort;
        @FiledName("citySort")
        public Integer getCitySort(){
            return this.citySort;
        }
        public void setCitySort(Integer citySort){
            this.citySort = citySort;
        }


        private Integer level;
        @FiledName("level")
        public Integer getLevel(){
            return this.level;
        }
        public void setLevel(Integer level){
            this.level = level;
        }


        private Timestamp createTime;
        @FiledName("createTime")
        public Timestamp getCreateTime(){
            return this.createTime;
        }
        public void setCreateTime(Timestamp createTime){
            this.createTime = createTime;
        }


        private Timestamp updateTime;
        @FiledName("updateTime")
        public Timestamp getUpdateTime(){
            return this.updateTime;
        }
        public void setUpdateTime(Timestamp updateTime){
            this.updateTime = updateTime;
        }


        private Integer operatorId;
        @FiledName("operatorId")
        public Integer getOperatorId(){
            return this.operatorId;
        }
        public void setOperatorId(Integer operatorId){
            this.operatorId = operatorId;
        }


        private String operatorName;
        @FiledName("operatorName")
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }


        private String isDeleted;
        @FiledName("isDeleted")
        public String getIsDeleted(){
            return this.isDeleted;
        }
        public void setIsDeleted(String isDeleted){
            this.isDeleted = isDeleted;
        }


        private String cityCode;
        @FiledName("cityCode")
        public String getCityCode() {
            return cityCode;
        }
        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        private String provinceCode;
        @FiledName("provinceCode")
        public String getProvinceCode() {
            return provinceCode;
        }
        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

}