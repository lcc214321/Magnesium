package net.onebean.server.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

public class FindApiByAppIdReq {

    /**
     * 服务ID
     */
    @NotEmpty(message = "param appId cannot be empty")
    private String appId;
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
}
