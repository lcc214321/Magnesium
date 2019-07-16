package net.onebean.app.mngt.vo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class AppBindingApiReq {

    @NotEmpty(message = "appId cannot be empty")
    private String appId;
    @NotEmpty(message = "apiIds cannot be empty")
    private List<String> apiIds;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<String> getApiIds() {
        return apiIds;
    }

    public void setApiIds(List<String> apiIds) {
        this.apiIds = apiIds;
    }
}
