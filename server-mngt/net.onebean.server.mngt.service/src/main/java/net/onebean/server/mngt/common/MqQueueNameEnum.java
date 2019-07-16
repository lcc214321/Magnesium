package net.onebean.server.mngt.common;

public enum MqQueueNameEnum {

    DEVOPS_UPDATE_SERVER_OR_API("devops.update.server.or.api"),
    DEVOPS_UPDATE_NGINX_UPSTEAM_NODE("devops.update.nginx.upsteam.node"),
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