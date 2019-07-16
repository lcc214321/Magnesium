package net.onebean.server.mngt.api.model;

public class ServerBasicInfo {

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 服务名称
     */
    private String serverName;
    public String getServerName(){
        return this.serverName;
    }
    public void setServerName(String serverName){
        this.serverName = serverName;
    }

}
