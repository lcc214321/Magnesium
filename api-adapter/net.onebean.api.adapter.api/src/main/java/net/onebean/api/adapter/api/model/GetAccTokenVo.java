package net.onebean.api.adapter.api.model;

/**
 * 获取AccToken 的请求体
 * @author 0neBean
 */
public class GetAccTokenVo {
    /*应用ID*/
    private String appId;
    /*应用秘钥*/
    private String secret;
    /*时间戳*/
    private String timestamp;
    /*签名*/
    private String accessToken;
    /*租户ID*/
    private String tenantId;
    /*设备码*/
    private String deviceToken;
    /*用户的ID*/
    private String customerId;

    private String accessTokenExpire;

    private String accessTokenCacheKey;

    public String getAccessTokenCacheKey() {
        return accessTokenCacheKey;
    }

    public void setAccessTokenCacheKey(String accessTokenCacheKey) {
        this.accessTokenCacheKey = accessTokenCacheKey;
    }

    public String getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(String accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
