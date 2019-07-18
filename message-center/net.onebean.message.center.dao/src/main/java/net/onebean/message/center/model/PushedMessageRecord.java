package net.onebean.message.center.model;
import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;

import net.onebean.core.model.InterfaceBaseSplitModel;


import java.sql.Timestamp;

/**
* @author 0neBean
* @description 推送记录 model
* @date 2019-07-18 16:39:04
*/
@TableName("t_pushed_message_record_")
public class PushedMessageRecord extends BaseModel implements InterfaceBaseSplitModel {



        /**
        * 1:验证码
        */
        private String messageType;
        @FiledName("message_type")
        public String getMessageType(){
            return this.messageType;
        }
        public void setMessageType(String messageType){
            this.messageType = messageType;
        }


        /**
        * 消息内容
        */
        private String messageBody;
        @FiledName("message_body")
        public String getMessageBody(){
            return this.messageBody;
        }
        public void setMessageBody(String messageBody){
            this.messageBody = messageBody;
        }


        /**
        * 接收消息的账号
        */
        private String receiverAccount;
        @FiledName("receiver_account")
        public String getReceiverAccount(){
            return this.receiverAccount;
        }
        public void setReceiverAccount(String receiverAccount){
            this.receiverAccount = receiverAccount;
        }


        /**
        * 接收消息的用户ID
        */
        private Integer receiverId;
        @FiledName("receiver_id")
        public Integer getReceiverId(){
            return this.receiverId;
        }
        public void setReceiverId(Integer receiverId){
            this.receiverId = receiverId;
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