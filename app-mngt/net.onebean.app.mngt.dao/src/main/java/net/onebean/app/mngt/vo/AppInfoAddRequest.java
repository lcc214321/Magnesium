package net.onebean.app.mngt.vo;

import net.onebean.core.Json.TimeStamp_Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

/**
 * @author 0neBean
 * @description 应用信息 model
 * @date 2019-01-03 16:14:09
 */
public class AppInfoAddRequest {

    @NotEmpty(message = "appName can not be empty")
    private String appName;

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @NotEmpty(message = "appCategory can not be empty")
    private String appCategory;

    public String getAppCategory() {
        return this.appCategory;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory;
    }

    private String authType;

    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @NotEmpty(message = "appStatus can not be empty")
    private String appStatus;

    public String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    @JsonDeserialize(using = TimeStamp_Deserializer.class)
    private Timestamp createTime;

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    private String loginType;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    private String mobilePageUrl;

    public String getMobilePageUrl() {
        return mobilePageUrl;
    }

    public void setMobilePageUrl(String mobilePageUrl) {
        this.mobilePageUrl = mobilePageUrl;
    }

    private String mobileMenuIcon;

    public String getMobileMenuIcon() {
        return mobileMenuIcon;
    }

    public void setMobileMenuIcon(String mobileMenuIcon) {
        this.mobileMenuIcon = mobileMenuIcon;
    }

    private String pcPageUrl;

    public String getPcPageUrl() {
        return pcPageUrl;
    }

    public void setPcPageUrl(String pcPageUrl) {
        this.pcPageUrl = pcPageUrl;
    }

    private String pcMenuIcon;

    public String getPcMenuIcon() {
        return pcMenuIcon;
    }

    public void setPcMenuIcon(String pcMenuIcon) {
        this.pcMenuIcon = pcMenuIcon;
    }


    private String oauthBaseUrl;

    public String getOauthBaseUrl() {
        return oauthBaseUrl;
    }

    public void setOauthBaseUrl(String oauthBaseUrl) {
        this.oauthBaseUrl = oauthBaseUrl;
    }

    private String menuSort;

    public String getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(String menuSort) {
        this.menuSort = menuSort;
    }
}