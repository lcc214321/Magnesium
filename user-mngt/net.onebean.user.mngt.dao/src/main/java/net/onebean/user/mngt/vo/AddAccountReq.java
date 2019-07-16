package net.onebean.user.mngt.vo;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author 0neBean
 * @description 用户信息 model
 * @date 2019-06-04 14:03:47
 */
public class AddAccountReq {


    @NotBlank(message = "filed of appId can not be empty")
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }



    /**
     * 用户名
     */
    @NotBlank(message = "filed of username can not be empty")
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @NotBlank(message = "filed of nickName can not be empty")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @NotBlank(message = "filed of password can not be empty")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}