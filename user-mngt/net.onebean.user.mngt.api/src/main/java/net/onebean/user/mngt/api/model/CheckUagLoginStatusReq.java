package net.onebean.user.mngt.api.model;

import org.hibernate.validator.constraints.NotBlank;

public class CheckUagLoginStatusReq {


    @NotBlank(message = "filed of appId can not be empty")
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @NotBlank(message = "filed of deviceToken can not be empty")
    private String deviceToken;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
