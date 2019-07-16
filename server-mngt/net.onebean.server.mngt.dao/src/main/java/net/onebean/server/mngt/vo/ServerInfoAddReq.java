package net.onebean.server.mngt.vo;

import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseModel;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-21 18:05:28
*/
public class ServerInfoAddReq extends BaseModel implements InterfaceBaseModel {


        /**
        * 服务名称
        */
        @NotEmpty(message = "serverName can not be empty")
        private String serverName;
        public String getServerName(){
            return this.serverName;
        }
        public void setServerName(String serverName){
            this.serverName = serverName;
        }


        /**
        * 部署类型 0:物理地址部署 1:marathon部署
        */
        @NotEmpty(message = "deployType can not be empty")
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
        @NotEmpty(message = "deployType can not be empty")
        public String getHostPath(){
            return this.hostPath;
        }
        public void setHostPath(String hostPath){
            this.hostPath = hostPath;
        }
}