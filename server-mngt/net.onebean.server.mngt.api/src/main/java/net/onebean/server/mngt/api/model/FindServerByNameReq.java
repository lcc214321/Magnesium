package net.onebean.server.mngt.api.model;

import org.hibernate.validator.constraints.NotEmpty;

public class FindServerByNameReq {
    /**
     * 服务名称
     */
    @NotEmpty(message = "serverName cannot be empty")
    private String serverName;
    public String getServerName(){
        return this.serverName;
    }
    public void setServerName(String serverName){
        this.serverName = serverName;
    }
}
