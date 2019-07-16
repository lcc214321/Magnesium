package net.onebean.server.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

public class AppApiRelationshipReq {

    /**
     * 服务ID
     */
    @NotEmpty(message = "param serverId cannot be empty")
    private String serverId;
    public String getServerId(){
        return this.serverId;
    }
    public void setServerId(String serverId){
        this.serverId = serverId;
    }

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
