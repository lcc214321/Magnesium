package net.onebean.server.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

/**
* @author 0neBean
* @description nginx节点管理 model
* @date 2019-03-01 12:00:33
*/
public class NginxNodeAddReq {

        /**
        * ip地址
        */
        @NotEmpty(message = "ipAddress can not be empty")
        private String ipAddress;
        public String getIpAddress(){
            return this.ipAddress;
        }
        public void setIpAddress(String ipAddress){
            this.ipAddress = ipAddress;
        }


        /**
         * 访问账户
         */
        @NotEmpty(message = "accessUser can not be empty")
        private String accessUser;
        public String getAccessUser(){
            return this.accessUser;
        }
        public void setAccessUser(String accessUser){
            this.accessUser = accessUser;
        }


        /**
         * 访问密码
         */
        private String accessPassword;
        public String getAccessPassword(){
            return this.accessPassword;
        }
        public void setAccessPassword(String accessPassword){
            this.accessPassword = accessPassword;
        }


        /**
         * 访问rsa_path
         */
        private String accessRsaPath;
        public String getAccessRsaPath(){
            return this.accessRsaPath;
        }
        public void setAccessRsaPath(String accessRsaPath){
            this.accessRsaPath = accessRsaPath;
        }


        /**
         * 访问端口号
         */
        @NotEmpty(message = "accessPort can not be empty")
        private String accessPort;
        public String getAccessPort(){
            return this.accessPort;
        }
        public void setAccessPort(String accessPort){
            this.accessPort = accessPort;
        }


        /**
         * 访问授权方式 (0: 密码模式,1:公私钥模式)
         */
        @NotEmpty(message = "accessAuthType can not be empty")
        private String accessAuthType;
        public String getAccessAuthType(){
            return this.accessAuthType;
        }
        public void setAccessAuthType(String accessAuthType){
            this.accessAuthType = accessAuthType;
        }


        /**
        * 配置文件地址
        */
        @NotEmpty(message = "confPath can not be empty")
        private String confPath;
        public String getConfPath(){
            return this.confPath;
        }
        public void setConfPath(String confPath){
            this.confPath = confPath;
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