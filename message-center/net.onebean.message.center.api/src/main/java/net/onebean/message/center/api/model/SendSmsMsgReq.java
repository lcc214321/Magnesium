package net.onebean.message.center.api.model;

import org.hibernate.validator.constraints.NotBlank;

public class SendSmsMsgReq {

    @NotBlank(message = "param filed of telPhone can not be empty")
    private String telPhone;

    @NotBlank(message = "param filed of messageBody can not be empty")
    private String messageBody;

    private String appId;


    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTelPhone() {
        return telPhone;
    }
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getMessageBody() {
        return messageBody;
    }
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
