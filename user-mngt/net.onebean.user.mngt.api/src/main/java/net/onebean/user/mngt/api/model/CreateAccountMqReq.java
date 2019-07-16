package net.onebean.user.mngt.api.model;

public class CreateAccountMqReq {

    private String uagUserId;
    private String uagUsername;

    public CreateAccountMqReq(String uagUserId, String uagUsername) {
        this.uagUserId = uagUserId;
        this.uagUsername = uagUsername;
    }

    public String getUagUserId() {
        return uagUserId;
    }

    public void setUagUserId(String uagUserId) {
        this.uagUserId = uagUserId;
    }

    public String getUagUsername() {
        return uagUsername;
    }

    public void setUagUsername(String uagUsername) {
        this.uagUsername = uagUsername;
    }

}
