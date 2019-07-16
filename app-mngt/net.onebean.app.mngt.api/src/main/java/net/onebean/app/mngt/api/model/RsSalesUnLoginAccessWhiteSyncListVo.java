package net.onebean.app.mngt.api.model;

import java.util.List;

public class RsSalesUnLoginAccessWhiteSyncListVo {

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
     * 白名单节点
     */
    private List<RsSalesUnLoginAccessWhiteVo> rsSalesUnLoginAccessWhiteListVos;
    public List<RsSalesUnLoginAccessWhiteVo> getRsSalesUnLoginAccessWhiteListVos() {
        return rsSalesUnLoginAccessWhiteListVos;
    }
    public void setRsSalesUnLoginAccessWhiteListVos(List<RsSalesUnLoginAccessWhiteVo> rsSalesUnLoginAccessWhiteListVos) {
        this.rsSalesUnLoginAccessWhiteListVos = rsSalesUnLoginAccessWhiteListVos;
    }
}
