package net.onebean.message.center.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseDeletedModel;


import java.sql.Timestamp;

/**
* @author 0neBean
* @description 短信验证码 model
* @date 2019-03-31 21:37:12
*/
@TableName("msg_code_record")
public class MsgCodeRecord extends BaseModel implements InterfaceBaseDeletedModel {



        /**
        * 类型 1登录短信验证码
        */
        private String msgType;
        @FiledName("msgType")
        public String getMsgType(){
            return this.msgType;
        }
        public void setMsgType(String msgType){
            this.msgType = msgType;
        }


        /**
        * 手机号
        */
        private String telphone;
        @FiledName("telphone")
        public String getTelphone(){
            return this.telphone;
        }
        public void setTelphone(String telphone){
            this.telphone = telphone;
        }


        /**
        * 验证码
        */
        private String msgCode;
        @FiledName("msgCode")
        public String getMsgCode(){
            return this.msgCode;
        }
        public void setMsgCode(String msgCode){
            this.msgCode = msgCode;
        }


        /**
        * 
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
        * 
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
        * 
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
        * 
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