package net.onebean.user.mngt.vo;

import net.onebean.core.Json.TimeStamp_Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Timestamp;

public class UagLogVo {


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

    /**
     * 项目名
     */
    private String appName;
    public String getAppName(){
        return this.appName;
    }
    public void setAppName(String appName){
        this.appName = appName;
    }


    /**
     * 用户名
     */
    private String operatorDescription;
    public String getOperatorDescription(){
        return this.operatorDescription;
    }
    public void setOperatorDescription(String operatorDescription){
        this.operatorDescription = operatorDescription;
    }


    /**
     * 创建时间
     */
    @JsonDeserialize(using = TimeStamp_Deserializer.class)
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
    @JsonDeserialize(using = TimeStamp_Deserializer.class)
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


}
