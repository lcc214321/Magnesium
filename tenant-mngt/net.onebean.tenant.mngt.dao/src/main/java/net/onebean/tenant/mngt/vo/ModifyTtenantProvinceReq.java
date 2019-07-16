package net.onebean.tenant.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

/**
* @author 0neBean
* @description 省份 model
* @date 2019-01-11 20:54:32
*/
public class ModifyTtenantProvinceReq {

        @NotEmpty(message = "id can not be empty")
        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        private String provinceName;
        public String getProvinceName(){
            return this.provinceName;
        }
        public void setProvinceName(String provinceName){
            this.provinceName = provinceName;
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

        private String provinceCode;
        public String getProvinceCode() {
            return provinceCode;
        }
        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }
}