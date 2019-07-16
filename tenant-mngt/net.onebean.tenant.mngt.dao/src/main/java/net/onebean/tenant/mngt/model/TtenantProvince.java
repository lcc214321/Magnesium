package net.onebean.tenant.mngt.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseDeletedModel;


import java.sql.Timestamp;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
@TableName("t_tenant_province")
public class TtenantProvince extends BaseModel implements InterfaceBaseDeletedModel {



        private String provinceName;
        @FiledName("provinceName")
        public String getProvinceName(){
            return this.provinceName;
        }
        public void setProvinceName(String provinceName){
            this.provinceName = provinceName;
        }


        private Integer proSort;
        @FiledName("proSort")
        public Integer getProSort(){
            return this.proSort;
        }
        public void setProSort(Integer proSort){
            this.proSort = proSort;
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

        private String provinceCode;
        @FiledName("provinceCode")
        public String getProvinceCode() {
            return provinceCode;
        }
        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }
}