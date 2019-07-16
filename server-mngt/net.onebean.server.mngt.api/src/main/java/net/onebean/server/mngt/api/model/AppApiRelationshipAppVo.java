package net.onebean.server.mngt.api.model;

import java.util.List;

public class AppApiRelationshipAppVo {

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    List<String> apiIds;

    public List<String> getApiIds() {
        return apiIds;
    }

    public void setApiIds(List<String> apiIds) {
        this.apiIds = apiIds;
    }
}

