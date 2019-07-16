package net.onebean.server.mngt.vo;

import net.onebean.core.Json.EnumDeserialize;
import net.onebean.server.mngt.enumModel.ApiStatusEnum;

import java.sql.Timestamp;

/**
* @author 0neBean
* @description server info model
* @date 2019-01-21 18:05:28
*/
public class ApiInfoVo  {

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
    @EnumDeserialize(using = ApiStatusEnum.class)
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


    /**
     * 创建时间
     */
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime = updateTime;
    }


    /**
     * 操作人ID
     */
    private Integer operatorId;
    public Integer getOperatorId(){
        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId){
        this.operatorId = operatorId;
    }


    /**
     * 操作人姓名
     */
    private String operatorName;
    public String getOperatorName(){
        return this.operatorName;
    }
    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }


}