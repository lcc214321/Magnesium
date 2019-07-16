package net.onebean.tenant.mngt.common;

public enum MqExchangeNameEnum {

            ;
    private String name;

    MqExchangeNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}