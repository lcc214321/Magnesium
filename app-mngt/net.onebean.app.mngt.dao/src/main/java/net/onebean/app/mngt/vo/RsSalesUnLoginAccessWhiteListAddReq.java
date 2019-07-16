package net.onebean.app.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
* @author 0neBean
* @description 易开小伙伴免登陆访问接口地址 model
* @date 2019-03-14 17:13:49
*/
public class RsSalesUnLoginAccessWhiteListAddReq {

        /**
        * 应用ID
        */
        @NotNull(message = "appId can not be empty")
        private Integer appId;
        public Integer getAppId(){
            return this.appId;
        }
        public void setAppId(Integer appId){
            this.appId = appId;
        }

        /**
         * 接口ID
         */
        @NotNull(message = "apiId can not be empty")
        private Integer apiId;
        public Integer getApiId() {
            return apiId;
        }
        public void setApiId(Integer apiId) {
            this.apiId = apiId;
        }


        /**
        * 接口名
        */
        @NotEmpty(message = "apiName can not be empty")
        private String apiName;
        public String getApiName(){
            return this.apiName;
        }
        public void setApiName(String apiName){
            this.apiName = apiName;
        }


        /**
        * API接口
        */
        @NotEmpty(message = "apiPath can not be empty")
        private String apiPath;
        public String getApiPath(){
            return this.apiPath;
        }
        public void setApiPath(String apiPath){
            this.apiPath = apiPath;
        }

        /**
        * 操作人ID
        */
        private Integer operatorId;
        public Integer getOperatorId(){
            return this.operatorId;
        }
        public void setOperatorId(Integer operatorId){
            this.operatorId = operatorId;
        }


        /**
        * 操作人姓名
        */
        private String operatorName;
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }

}