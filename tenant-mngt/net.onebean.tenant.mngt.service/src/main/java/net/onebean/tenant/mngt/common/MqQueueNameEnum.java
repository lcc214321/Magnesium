package net.onebean.tenant.mngt.common;

public enum MqQueueNameEnum {
    DEVOPS_TENANT_INFO_INIT("devops.tenant.info.init.tenant.account"),
    DEVOPS_TENANT_INFO_SYNC_TENANT_ACCOUNT("devops.tenant.info.sync.tenant.account"),
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
