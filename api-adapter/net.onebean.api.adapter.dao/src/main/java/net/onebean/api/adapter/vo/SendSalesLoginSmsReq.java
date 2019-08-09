package net.onebean.api.adapter.vo;

import org.hibernate.validator.constraints.NotBlank;

public class SendSalesLoginSmsReq {

    @NotBlank(message = "param of filed telPhone can not be empty")
    private String telPhone;

    public String getTelPhone() {
        return telPhone;
    }
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

}
