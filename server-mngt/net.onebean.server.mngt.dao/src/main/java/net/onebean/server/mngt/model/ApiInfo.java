package net.onebean.server.mngt.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description api info model
* @date 2019-01-22 10:59:03
*/
@TableName("t_api_info")
public class ApiInfo extends BaseModel implements InterfaceBaseDeletedModel {

        /**
        * 接口名称
        */
        private String apiName;
        @FiledName("apiName")
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
        @FiledName("proxyPath")
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
        @FiledName("apiUri")
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
        @FiledName("apiStatus")
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
        @FiledName("serverId")
        public Integer getServerId(){
            return this.serverId;
        }
        public void setServerId(Integer serverId){
            this.serverId = serverId;
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