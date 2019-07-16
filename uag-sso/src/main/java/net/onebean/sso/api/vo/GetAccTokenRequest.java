package net.onebean.sso.api.vo;

/**
 * 获取AccToken 的请求体
 * @author 0neBean
 */
public class GetAccTokenRequest {
    /*应用ID*/
    private String appId;
    /*应用秘钥*/
    private String secret;
    /*时间戳*/
    private String timestamp;
    /*签名*/
    private String sign;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
