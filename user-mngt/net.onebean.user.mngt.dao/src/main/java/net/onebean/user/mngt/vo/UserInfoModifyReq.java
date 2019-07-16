package net.onebean.user.mngt.vo;

import org.hibernate.validator.constraints.NotBlank;

public class UserInfoModifyReq {

    @NotBlank(message = "filed of appId can not be empty")
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    @NotBlank(message = "id can not be empty")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank(message = "nickName can not be empty")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 操作人ID
     */
    private Integer operatorId;

    public Integer getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
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

}
