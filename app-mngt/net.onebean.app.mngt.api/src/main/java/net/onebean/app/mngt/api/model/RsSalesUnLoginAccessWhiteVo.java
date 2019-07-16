package net.onebean.app.mngt.api.model;

public class RsSalesUnLoginAccessWhiteVo {

    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 接口ID
     */
    private String apiId;
    public String getApiId() {
        return apiId;
    }
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }


    /**
     * 应用ID
     */
    private String appId;
    public String getAppId(){
        return this.appId;
    }
    public void setAppId(String appId){
        this.appId = appId;
    }


    /**
     * 接口名
     */
    private String apiName;
    public String getApiName(){
        return this.apiName;
    }
    public void setApiName(String apiName){
        this.apiName = apiName;
    }


    /**
     * API接口
     */
    private String apiPath;
    public String getApiPath(){
        return this.apiPath;
    }
    public void setApiPath(String apiPath){
        this.apiPath = apiPath;
    }
}
