package net.onebean.user.mngt.vo;

import net.onebean.core.Json.TimeStamp_Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Timestamp;

/**
 * @author 0neBean
 * @description 用户信息 model
 * @date 2019-06-04 14:03:47
 */
public class UagUserInfoVo {


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 用户名
     */
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    /**
     * 创建时间
     */
    @JsonDeserialize(using = TimeStamp_Deserializer.class)
    private Timestamp createTime;

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    @JsonDeserialize(using = TimeStamp_Deserializer.class)
    private Timestamp updateTime;

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * 操作人姓名
     */
    private String operatorName;

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    private String isLock;

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

}