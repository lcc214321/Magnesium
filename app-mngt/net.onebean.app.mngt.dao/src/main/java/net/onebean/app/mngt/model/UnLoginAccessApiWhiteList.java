package net.onebean.app.mngt.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseDeletedModel;


import java.sql.Timestamp;

/**
* @author 0neBean
* @description 未登录访问API白名单 model
* @date 2019-06-28 11:02:32
*/
@TableName("t_un_login_access_api_white_list")
public class UnLoginAccessApiWhiteList extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * 接口ID
        */
        private Integer apiId;
        @FiledName("api_id")
        public Integer getApiId(){
            return this.apiId;
        }
        public void setApiId(Integer apiId){
            this.apiId = apiId;
        }


        /**
        * 应用ID
        */
        private Integer appId;
        @FiledName("app_id")
        public Integer getAppId(){
            return this.appId;
        }
        public void setAppId(Integer appId){
            this.appId = appId;
        }


        /**
        * 应用名
        */
        private String apiName;
        @FiledName("api_name")
        public String getApiName(){
            return this.apiName;
        }
        public void setApiName(String apiName){
            this.apiName = apiName;
        }


        /**
        * API接口
        */
        private String apiPath;
        @FiledName("api_path")
        public String getApiPath(){
            return this.apiPath;
        }
        public void setApiPath(String apiPath){
            this.apiPath = apiPath;
        }


        /**
        * 创建时间
        */
        private Timestamp createTime;
        @FiledName("create_time")
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
        @FiledName("update_time")
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
        @FiledName("operator_id")
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
        @FiledName("operator_name")
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
        @FiledName("is_deleted")
        public String getIsDeleted(){
            return this.isDeleted;
        }
        public void setIsDeleted(String isDeleted){
            this.isDeleted = isDeleted;
        }




}