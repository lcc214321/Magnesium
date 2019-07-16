package net.onebean.server.mngt.vo;

import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseModel;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-21 18:05:28
*/
public class ApiInfoFindReq extends BaseModel implements InterfaceBaseModel {


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