package net.onebean.server.mngt.vo;

import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseModel;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-21 18:05:28
*/
public class ApiInfoAddReq{


    /**
     * 接口名称
     */
    @NotEmpty(message = "apiName can not be empty")
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
    @NotEmpty(message = "apiUri can not be empty")
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
    @NotEmpty(message = "proxyPath can not be empty")
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
    @NotEmpty(message = "apiStatus can not be empty")
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
    @NotNull(message = "serverId can not be empty")
    private Integer serverId;
    public Integer getServerId(){
        return this.serverId;
    }
    public void setServerId(Integer serverId){
        this.serverId = serverId;
    }
}