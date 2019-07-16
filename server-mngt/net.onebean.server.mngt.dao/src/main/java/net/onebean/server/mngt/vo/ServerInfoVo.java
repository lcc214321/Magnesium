package net.onebean.server.mngt.vo;

import net.onebean.core.Json.EnumDeserialize;
import net.onebean.server.mngt.enumModel.DeployTypeEnum;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-21 18:05:28
*/
public class ServerInfoVo{

        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

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
        @EnumDeserialize(using = DeployTypeEnum.class)
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
        * 创建时间
        */
        private Timestamp createTime;
        public Timestamp getCreateTime(){
            return this.createTime;
        }
        public void setCreateTime(Timestamp createTime){
            this.createTime = createTime;
        }


        /**
        * 更新时间
        */
        private Timestamp updateTime;
        public Timestamp getUpdateTime(){
            return this.updateTime;
        }
        public void setUpdateTime(Timestamp updateTime){
            this.updateTime = updateTime;
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