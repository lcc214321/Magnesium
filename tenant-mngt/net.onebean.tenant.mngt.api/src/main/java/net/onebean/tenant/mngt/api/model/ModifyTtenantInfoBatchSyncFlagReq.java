package net.onebean.tenant.mngt.api.model;

import org.hibernate.validator.constraints.NotBlank;

/**
* @author 0neBean
* @description 租户信息 model
* @date 2019-01-11 20:56:12
*/
public class ModifyTtenantInfoBatchSyncFlagReq {

        @NotBlank(message = "field of ids can not be blank")
        private String ids;
        public String getIds() {
            return ids;
        }
        public void setIds(String ids) {
            this.ids = ids;
        }

        @NotBlank(message = "field of isSync can not be blank")
        private String isSync;
        public String getIsSync() {
            return isSync;
        }
        public void setIsSync(String isSync) {
            this.isSync = isSync;
        }
}