package net.onebean.message.center.vo;

import java.net.URLEncoder;

public class Channel2SendParamVo {

    private String message;
    private String sysName;
    private String type;
    private String phones;
    private String token;

    public Channel2SendParamVo() {
    }

    public Channel2SendParamVo(String message, String sysName, String type, String phones, String token) {
        this.message = message;
        this.sysName = sysName;
        this.type = type;
        this.phones = phones;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
