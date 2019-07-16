package net.onebean.api.adapter.common;

public enum MqQueueNameEnum {
    MESSAGE_CENTER_SEND_SMS_MESSAGE("message.center.send.sms.message");
    private String name;

    MqQueueNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}