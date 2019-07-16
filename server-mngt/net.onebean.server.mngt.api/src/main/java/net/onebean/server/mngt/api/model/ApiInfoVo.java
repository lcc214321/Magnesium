package net.onebean.server.mngt.api.model;

/**
* @author 0neBean
* @description api info model
* @date 2019-01-22 10:59:03
*/
public class ApiInfoVo  {

        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        /**
        * 接口名称
        */
        private String apiName;
        public String getApiName(){
            return this.apiName;
        }
        public void setApiName(String apiName){
            this.apiName = apiName;
        }


        /**
         * 代理地址
         */
        private String proxyPath;
        public String getProxyPath() {
            return proxyPath;
        }
        public void setProxyPath(String proxyPath) {
            this.proxyPath = proxyPath;
        }


        /**
            * 接口地址
            */
        private String apiUri;
        public String getApiUri(){
            return this.apiUri;
        }
        public void setApiUri(String apiUri){
            this.apiUri = apiUri;
        }


        /**
        * 接口状态 0:未启用 1启用
        */
        private String apiStatus;
        public String getApiStatus(){
            return this.apiStatus;
        }
        public void setApiStatus(String apiStatus){
            this.apiStatus = apiStatus;
        }


        /**
        * 服务ID
        */
        private Integer serverId;
        public Integer getServerId(){
            return this.serverId;
        }
        public void setServerId(Integer serverId){
            this.serverId = serverId;
        }

        /**
         * 部署类型 0:物理地址部署 1:marathon部署
         */
        private String deployType;
        public String getDeployType(){
            return this.deployType;
        }
        public void setDeployType(String deployType){
            this.deployType = deployType;
        }

        /**
         * 访问地址
         */
        private String hostPath;
        public String getHostPath(){
            return this.hostPath;
        }
        public void setHostPath(String hostPath){
            this.hostPath = hostPath;
        }

}