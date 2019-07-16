package net.onebean.app.mngt.api.model;

import org.hibernate.validator.constraints.NotBlank;

public class FindUagUserAppListReq {


    @NotBlank(message = "filed of appIds can not be empty")
    private String appIds;

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }
}
