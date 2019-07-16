package net.onebean.user.mngt.common;

public enum MqExchangeNameEnum {

    UAG_CREATE_ACCOUNT_FANOUT_EXCHANGE("uag.create.account.fanout.exchange");
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