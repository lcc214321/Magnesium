package net.onebean.server.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

public class ApiModifyReq {

    /**
     * 服务名称
     */
    @NotEmpty(message = "id can not be empty")
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 接口名称
     */
    private String apiName;
    public String getApiName(){
        return this.apiName;
    }
    public void setApiName(String apiName){
        this.apiName = apiName;
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
     * 代理地址
     */
    private String proxyPath;
    public String getProxyPath() {
        return proxyPath;
    }
    public void setProxyPath(String proxyPath) {
        this.proxyPath = proxyPath;
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
