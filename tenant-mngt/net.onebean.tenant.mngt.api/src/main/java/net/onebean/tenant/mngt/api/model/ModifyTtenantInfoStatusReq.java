package net.onebean.tenant.mngt.api.model;

import org.hibernate.validator.constraints.NotBlank;

/**
* @author 0neBean
* @description 租户信息 model
* @date 2019-01-11 20:56:12
*/
public class ModifyTtenantInfoStatusReq {

        @NotBlank(message = "id can not be empty")
        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        @NotBlank(message = "status can not be empty")
        private String status;
        public String getStatus(){
            return this.status;
        }
        public void setStatus(String status){
            this.status = status;
        }



}