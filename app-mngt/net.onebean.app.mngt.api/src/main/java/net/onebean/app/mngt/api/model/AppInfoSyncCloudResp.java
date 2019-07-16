package net.onebean.app.mngt.api.model;

/**
* @author 0neBean
* @description 应用信息 model
* @date 2019-01-03 16:14:09
*/
public class AppInfoSyncCloudResp {


        private String appId;
        public String getAppId() {
            return appId;
        }
        public void setAppId(String appId) {
                this.appId = appId;
            }


        private AppInfoSyncVo   appInfo;
        public AppInfoSyncVo  getAppInfo() {
            return appInfo;
        }
        public void setAppInfo(AppInfoSyncVo  appInfo) {
            this.appInfo = appInfo;
        }
}