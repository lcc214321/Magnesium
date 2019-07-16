package net.onebean.server.mngt.api.model;

public class UriApiRelationshipVo {

    private String path;
    private ApiInfoVo apiInfo;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ApiInfoVo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfoVo apiInfo) {
        this.apiInfo = apiInfo;
    }
}
