package net.onebean.uag.log.common;

public enum ConstantEnum {
    UAG_CLOUD_CONTROL_OPERATION_LOG("uag.cloud.control.operation.log");


    private String name;

    ConstantEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
