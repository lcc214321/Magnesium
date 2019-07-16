package net.onebean.sso.api.common;

public enum ConstantEnum {

    UAG_SSO_LOGIN_INFO("UAG-SSO-LOGIN-INFO"),
    UAG_DEVICE_TOKEN_PARAM_KEY("uagDeviceToken");
    
    private String name;

    ConstantEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
