package net.onebean.user.mngt.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author 0neBean
 * @description 用户信息 model
 * @date 2019-06-04 14:03:47
 */
public class FindUagUserInfoReq {


    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     * 用户名
     */
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * 密码
     */
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @NotBlank(message = "filed of appId can not be empty")
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}