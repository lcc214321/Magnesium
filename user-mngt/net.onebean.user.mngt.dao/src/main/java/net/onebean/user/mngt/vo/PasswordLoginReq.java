package net.onebean.user.mngt.vo;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordLoginReq {

    @NotBlank(message = "filed of telPhone can not be empty")
    private String telPhone;
    @NotBlank(message = "filed of password can not be empty")
    private String password;

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
