package net.onebean.app.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

public class FindAppByAppIdAndSecretReq {

    @NotEmpty(message = "openId can not be empty")
    private String openId;

    @NotEmpty(message = "secret can not be empty")
    private String secret;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
