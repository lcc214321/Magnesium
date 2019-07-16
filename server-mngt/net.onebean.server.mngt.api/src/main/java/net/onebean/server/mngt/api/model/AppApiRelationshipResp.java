package net.onebean.server.mngt.api.model;

import java.util.List;

public class AppApiRelationshipResp {
    private List<ApiInfoCloudVo> allApi;
    private List<String> bindKeys;

    public List<ApiInfoCloudVo> getAllApi() {
        return allApi;
    }

    public void setAllApi(List<ApiInfoCloudVo> allApi) {
        this.allApi = allApi;
    }

    public List<String> getBindKeys() {
        return bindKeys;
    }

    public void setBindKeys(List<String> bindKeys) {
        this.bindKeys = bindKeys;
    }
}
