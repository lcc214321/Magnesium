package net.onebean.app.mngt.common;

public enum MqQueueNameEnum {
    DEVOPS_UPDATE_SERVER_OR_API("devops.update.server.or.api"),
    DEVOPS_INIT_UAG_ACCOUNT_TABLE("devops.init.uag.account.table"),
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