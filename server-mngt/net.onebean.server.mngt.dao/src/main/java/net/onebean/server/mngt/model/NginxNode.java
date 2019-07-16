package net.onebean.server.mngt.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseDeletedModel;


import java.sql.Timestamp;

/**
* @author 0neBean
* @description nginx node info model
* @date 2019-03-02 15:47:15
*/
@TableName("t_nginx_node")
public class NginxNode extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * ip地址
        */
        private String ipAddress;
        @FiledName("ipAddress")
        public String getIpAddress(){
            return this.ipAddress;
        }
        public void setIpAddress(String ipAddress){
            this.ipAddress = ipAddress;
        }


        /**
        * 访问账户
        */
        private String accessUser;
        @FiledName("accessUser")
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
        @FiledName("accessPassword")
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
        @FiledName("accessRsaPath")
        public String getAccessRsaPath(){
            return this.accessRsaPath;
        }
        public void setAccessRsaPath(String accessRsaPath){
            this.accessRsaPath = accessRsaPath;
        }


        /**
        * 访问端口号
        */
        private String accessPort;
        @FiledName("accessPort")
        public String getAccessPort(){
            return this.accessPort;
        }
        public void setAccessPort(String accessPort){
            this.accessPort = accessPort;
        }


        /**
        * 访问授权方式 (0: 密码模式,1:公私钥模式)
        */
        private String accessAuthType;
        @FiledName("accessAuthType")
        public String getAccessAuthType(){
            return this.accessAuthType;
        }
        public void setAccessAuthType(String accessAuthType){
            this.accessAuthType = accessAuthType;
        }


        /**
        * 配置文件地址
        */
        private String confPath;
        @FiledName("confPath")
        public String getConfPath(){
            return this.confPath;
        }
        public void setConfPath(String confPath){
            this.confPath = confPath;
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