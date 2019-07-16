package net.onebean.server.mngt.api.model;

import java.util.List;

public class AppApiRelationSyncResqVo {

    private List<AppApiRelationshipAppVo> appApiList;
    private List<UriApiRelationshipVo> uriApiList;

    public List<AppApiRelationshipAppVo> getAppApiList() {
        return appApiList;
    }

    public void setAppApiList(List<AppApiRelationshipAppVo> appApiList) {
        this.appApiList = appApiList;
    }

    public List<UriApiRelationshipVo> getUriApiList() {
        return uriApiList;
    }

    public void setUriApiList(List<UriApiRelationshipVo> uriApiList) {
        this.uriApiList = uriApiList;
    }
}
