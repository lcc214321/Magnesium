package net.onebean.server.mngt.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-22 09:13:50
*/
@TableName("t_server_info")
public class ServerInfo extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * 服务名称
        */
        private String serverName;
        @FiledName("serverName")
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
        @FiledName("deployType")
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
        @FiledName("hostPath")
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
        @FiledName("createTime")
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
        @FiledName("updateTime")
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
        @FiledName("operatorId")
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
        @FiledName("operatorName")
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }


        /**
        * 逻辑删除,0否1是
        */
        private String isDeleted;
        @FiledName("isDeleted")
        public String getIsDeleted(){
            return this.isDeleted;
        }
        public void setIsDeleted(String isDeleted){
            this.isDeleted = isDeleted;
        }




}