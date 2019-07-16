package net.onebean.app.mngt.vo;

/**
* @author 0neBean
* @description 应用信息 model
* @date 2019-01-03 16:14:09
*/
public class AppInfoSyncResp {


        private String appId;
        public String getAppId() {
            return appId;
        }
        public void setAppId(String appId) {
                this.appId = appId;
            }


        private AppInfoVo  appInfo;
        public AppInfoVo getAppInfo() {
            return appInfo;
        }
        public void setAppInfo(AppInfoVo appInfo) {
            this.appInfo = appInfo;
        }
}