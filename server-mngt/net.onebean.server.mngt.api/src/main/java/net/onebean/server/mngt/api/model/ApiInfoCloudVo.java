package net.onebean.server.mngt.api.model;

public class ApiInfoCloudVo {

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 接口名称
     */
    private String label;
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 接口地址
     */
    private String apiUri;
    public String getApiUri(){
        return this.apiUri;
    }
    public void setApiUri(String apiUri){
        this.apiUri = apiUri;
    }


    /**
     * 接口状态 0:未启用 1启用
     */
    private String apiStatus;
    public String getApiStatus(){
        return this.apiStatus;
    }
    public void setApiStatus(String apiStatus){
        this.apiStatus = apiStatus;
    }


    /**
     * 服务ID
     */
    private Integer serverId;
    public Integer getServerId(){
        return this.serverId;
    }
    public void setServerId(Integer serverId){
        this.serverId = serverId;
    }
}
