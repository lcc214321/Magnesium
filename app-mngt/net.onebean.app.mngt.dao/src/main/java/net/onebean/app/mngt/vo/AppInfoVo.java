package net.onebean.app.mngt.vo;

import net.onebean.app.mngt.api.model.constant.AppInfoAppCategoryEnum;
import net.onebean.app.mngt.api.model.constant.AuthTypeEnum;
import net.onebean.app.mngt.enumModel.AppStatusEnum;
import net.onebean.app.mngt.api.model.constant.LoginTypeEnum;
import net.onebean.core.Json.EnumDeserialize;
import net.onebean.core.Json.TimeStamp_Deserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Timestamp;

/**
 * @author 0neBean
 * @description 应用信息 model
 * @date 2019-01-03 16:14:09
 */
public class AppInfoVo {


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String appVersion;

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @EnumDeserialize(using = AuthTypeEnum.class)
    private String authType;

    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    private String appName;

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @EnumDeserialize(using = AppInfoAppCategoryEnum.class)
    private String appCategory;

    public String getAppCategory() {
        return this.appCategory;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory;
    }

    @EnumDeserialize(using = AppStatusEnum.class)
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

    @JsonDeserialize(using = TimeStamp_Deserializer.class)
    private Timestamp updateTime;

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    private Integer operatorId;

    public Integer getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    private String operatorName;

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * inner api 访问令牌
     */
    private String innerApiToken;

    public String getInnerApiToken() {
        return this.innerApiToken;
    }

    public void setInnerApiToken(String innerApiToken) {
        this.innerApiToken = innerApiToken;
    }


    /**
     * 应用ID
     */
    private String openId;

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    /**
     * 秘钥
     */
    private String secret;

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    @EnumDeserialize(using = LoginTypeEnum.class)
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