package net.onebean.uag.log.vo;

public class UagOperationLogMqReq {

    String description;
    String appId;
    String uagUserId;
    String uagUserNickName;

    public UagOperationLogMqReq() {
    }

    public UagOperationLogMqReq(String description, String appId, String uagUserId, String uagUserNickName) {
        this.description = description;
        this.appId = appId;
        this.uagUserId = uagUserId;
        this.uagUserNickName = uagUserNickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUagUserId() {
        return uagUserId;
    }

    public void setUagUserId(String uagUserId) {
        this.uagUserId = uagUserId;
    }

    public String getUagUserNickName() {
        return uagUserNickName;
    }

    public void setUagUserNickName(String uagUserNickName) {
        this.uagUserNickName = uagUserNickName;
    }
}
