package net.onebean.app.mngt.vo;

/**
* @author 0neBean
* @description 应用信息 model
* @date 2019-01-03 16:14:09
*/
public class AppInfoQueryRequest {

        private String id;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        private String appVersion;
        public String getAppVersion(){
            return this.appVersion;
        }
        public void setAppVersion(String appVersion){
            this.appVersion = appVersion;
        }

        private String appName;
        public String getAppName(){
            return this.appName;
        }
        public void setAppName(String appName){
            this.appName = appName;
        }

        private String appCategory;
        public String getAppCategory(){
            return this.appCategory;
        }
        public void setAppCategory(String appCategory){
            this.appCategory = appCategory;
        }

        private String appStatus;
        public String getAppStatus(){
            return this.appStatus;
        }
        public void setAppStatus(String appStatus){
            this.appStatus = appStatus;
        }
}