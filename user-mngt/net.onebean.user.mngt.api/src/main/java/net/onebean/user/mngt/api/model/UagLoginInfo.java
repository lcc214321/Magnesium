package net.onebean.user.mngt.api.model;

public class UagLoginInfo {

    private String loginStatus;
    private String uagUserId;
    private String loginStatusComment;
    private String oauthBaseUrl;
    private String uagUserNickName;
    private String uagUsername;

    public String getUagUsername() {
        return uagUsername;
    }

    public void setUagUsername(String uagUsername) {
        this.uagUsername = uagUsername;
    }

    public String getUagUserNickName() {
        return uagUserNickName;
    }

    public void setUagUserNickName(String uagUserNickName) {
        this.uagUserNickName = uagUserNickName;
    }

    public String getOauthBaseUrl() {
        return oauthBaseUrl;
    }

    public void setOauthBaseUrl(String oauthBaseUrl) {
        this.oauthBaseUrl = oauthBaseUrl;
    }

    public String getUagUserId() {
        return uagUserId;
    }

    public void setUagUserId(String uagUserId) {
        this.uagUserId = uagUserId;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getLoginStatusComment() {
        return loginStatusComment;
    }

    public void setLoginStatusComment(String loginStatusComment) {
        this.loginStatusComment = loginStatusComment;
    }
}
