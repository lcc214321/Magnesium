package net.onebean.uag.conf.api.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class SendLoginSmsReq {

    @NotBlank(message = "param of filed deviceToken can not be empty")
    private String deviceToken;
    @NotBlank(message = "param of filed telPhone can not be empty")
    private String telPhone;
    @NotBlank(message = "param of filed smsCode can not be empty")
    private String smsCode;
    @Range(message = "param of filed timeOut can not be empty")
    private Long timeOut;

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
