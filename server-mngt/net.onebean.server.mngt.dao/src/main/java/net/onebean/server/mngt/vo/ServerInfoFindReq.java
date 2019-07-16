package net.onebean.server.mngt.vo;

import net.onebean.core.extend.FiledName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseModel;
import org.hibernate.validator.constraints.NotEmpty;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-21 18:05:28
*/
public class ServerInfoFindReq extends BaseModel implements InterfaceBaseModel {


        /**
        * 服务名称
        */
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