package net.onebean.app.mngt.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseDeletedModel;


import java.sql.Timestamp;

/**
* @author 0neBeen
* @description ip白名单 model
* @date 2019-02-21 15:48:15
*/
@TableName("t_ip_white_list")
public class IpWhiteList extends BaseModel implements InterfaceBaseDeletedModel {



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