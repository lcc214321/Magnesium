package net.onebean.app.mngt.api.model;

import java.util.ArrayList;
import java.util.List;

public class UagSyncDataResp {

    private List<IpWhiteListVo> ipWhiteListVos = new ArrayList<>();
    private List<AppInfoSyncCloudResp> appInfoSyncCloudRespList = new ArrayList<>();
    private List<RsSalesUnLoginAccessWhiteSyncListVo> unLoginAccessWhiteListSyncList;

    public List<RsSalesUnLoginAccessWhiteSyncListVo> getUnLoginAccessWhiteListSyncList() {
        return unLoginAccessWhiteListSyncList;
    }
    public void setUnLoginAccessWhiteListSyncList(List<RsSalesUnLoginAccessWhiteSyncListVo> unLoginAccessWhiteListSyncList) {
        this.unLoginAccessWhiteListSyncList = unLoginAccessWhiteListSyncList;
    }

    public List<IpWhiteListVo> getIpWhiteListVos() {
        return ipWhiteListVos;
    }
    public void setIpWhiteListVos(List<IpWhiteListVo> ipWhiteListVos) {
        this.ipWhiteListVos = ipWhiteListVos;
    }

    public List<AppInfoSyncCloudResp> getAppInfoSyncCloudRespList() {
        return appInfoSyncCloudRespList;
    }
    public void setAppInfoSyncCloudRespList(List<AppInfoSyncCloudResp> appInfoSyncCloudRespList) {
        this.appInfoSyncCloudRespList = appInfoSyncCloudRespList;
    }
}
