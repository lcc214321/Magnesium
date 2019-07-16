package net.onebean.tenant.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
public class AddTtenantProvinceReq {

        @NotEmpty(message = "provinceName can not be empty")
        private String provinceName;
        public String getProvinceName(){
            return this.provinceName;
        }
        public void setProvinceName(String provinceName){
            this.provinceName = provinceName;
        }

        @Range(message = "proSort can not be empty")
        private Integer proSort;
        public Integer getProSort(){
            return this.proSort;
        }
        public void setProSort(Integer proSort){
            this.proSort = proSort;
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

        @NotEmpty(message = "provinceCode can not be empty")
        private String provinceCode;
        public String getProvinceCode() {
            return provinceCode;
        }
        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }
}