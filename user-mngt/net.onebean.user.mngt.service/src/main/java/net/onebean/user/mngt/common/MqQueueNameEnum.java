package net.onebean.user.mngt.common;

public enum MqQueueNameEnum {


    UAG_CLOUD_CONTROL_OPERATION_LOG("uag.cloud.control.operation.log"),
    ;

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