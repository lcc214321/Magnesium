package net.onebean.app.mngt.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseDeletedModel;

import java.sql.Timestamp;

/**
 * @author 0neBean
 * @description app info model
 * @date 2019-01-21 17:18:49
 */
@TableName("t_app_info")
public class AppInfo extends BaseModel implements InterfaceBaseDeletedModel {


    /**
     * app版本
     */
    private String appVersion;

    @FiledName("appVersion")
    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }


    /**
     * 授权模式 0:innerAPi,1:openApi
     */
    private String authType;

    @FiledName("authType")
    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }


    /**
     * inner api 访问令牌
     */
    private String innerApiToken;

    @FiledName("innerApiToken")
    public String getInnerApiToken() {
        return this.innerApiToken;
    }

    public void setInnerApiToken(String innerApiToken) {
        this.innerApiToken = innerApiToken;
    }


    /**
     *
     */
    private String openId;

    @FiledName("openId")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    /**
     * 秘钥
     */
    private String secret;

    @FiledName("secret")
    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }


    /**
     * app名称
     */
    private String appName;

    @FiledName("appName")
    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    /**
     * app分类 0:基础应用,1:普通应用,2云应用
     */
    private String appCategory;

    @FiledName("appCategory")
    public String getAppCategory() {
        return this.appCategory;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory;
    }


    /**
     * app状态 0:连接 1:断开
     */
    private String appStatus;

    @FiledName("appStatus")
    public String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }


    /**
     * 创建时间
     */
    private Timestamp createTime;

    @FiledName("createTime")
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    private Timestamp updateTime;

    @FiledName("updateTime")
    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * 操作人ID
     */
    private Integer operatorId;

    @FiledName("operatorId")
    public Integer getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }


    /**
     * 操作人姓名
     */
    private String operatorName;

    @FiledName("operatorName")
    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    /**
     * 逻辑删除,0否1是
     */
    private String isDeleted;

    @FiledName("isDeleted")
    public String getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }


    private String mobilePageUrl;

    @FiledName("mobilePageUrl")
    public String getMobilePageUrl() {
        return mobilePageUrl;
    }

    public void setMobilePageUrl(String mobilePageUrl) {
        this.mobilePageUrl = mobilePageUrl;
    }

    private String mobileMenuIcon;

    @FiledName("mobileMenuIcon")
    public String getMobileMenuIcon() {
        return mobileMenuIcon;
    }

    public void setMobileMenuIcon(String mobileMenuIcon) {
        this.mobileMenuIcon = mobileMenuIcon;
    }

    private String pcPageUrl;

    @FiledName("pcPageUrl")
    public String getPcPageUrl() {
        return pcPageUrl;
    }

    public void setPcPageUrl(String pcPageUrl) {
        this.pcPageUrl = pcPageUrl;
    }

    private String pcMenuIcon;

    @FiledName("pcMenuIcon")
    public String getPcMenuIcon() {
        return pcMenuIcon;
    }

    public void setPcMenuIcon(String pcMenuIcon) {
        this.pcMenuIcon = pcMenuIcon;
    }

    private String loginType;

    @FiledName("loginType")
    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    private String oauthBaseUrl;

    @FiledName("oauth_base_url")
    public String getOauthBaseUrl() {
        return oauthBaseUrl;
    }

    public void setOauthBaseUrl(String oauthBaseUrl) {
        this.oauthBaseUrl = oauthBaseUrl;
    }

    private String menuSort;

    @FiledName("menu_sort")
    public String getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(String menuSort) {
        this.menuSort = menuSort;
    }
}