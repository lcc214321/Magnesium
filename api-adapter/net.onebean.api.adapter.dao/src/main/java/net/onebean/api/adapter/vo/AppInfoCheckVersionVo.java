package net.onebean.api.adapter.vo;

import java.util.List;

public class AppInfoCheckVersionVo {

    private String androidCurrentVersion;
    private String iosCurrentVersion;
    private String iosUpdateUrl;
    private String androidApkDownloadUrl;
    private List<String> updateInfoComment;
    private String iosUpdateFlag;
    private String androidUpdateFlag;

    public List<String> getUpdateInfoComment() {
        return updateInfoComment;
    }

    public void setUpdateInfoComment(List<String> updateInfoComment) {
        this.updateInfoComment = updateInfoComment;
    }

    public String getAndroidCurrentVersion() {
        return androidCurrentVersion;
    }

    public void setAndroidCurrentVersion(String androidCurrentVersion) {
        this.androidCurrentVersion = androidCurrentVersion;
    }

    public String getIosCurrentVersion() {
        return iosCurrentVersion;
    }

    public void setIosCurrentVersion(String iosCurrentVersion) {
        this.iosCurrentVersion = iosCurrentVersion;
    }

    public String getIosUpdateUrl() {
        return iosUpdateUrl;
    }

    public void setIosUpdateUrl(String iosUpdateUrl) {
        this.iosUpdateUrl = iosUpdateUrl;
    }

    public String getAndroidApkDownloadUrl() {
        return androidApkDownloadUrl;
    }

    public void setAndroidApkDownloadUrl(String androidApkDownloadUrl) {
        this.androidApkDownloadUrl = androidApkDownloadUrl;
    }

    public String getIosUpdateFlag() {
        return iosUpdateFlag;
    }

    public void setIosUpdateFlag(String iosUpdateFlag) {
        this.iosUpdateFlag = iosUpdateFlag;
    }

    public String getAndroidUpdateFlag() {
        return androidUpdateFlag;
    }

    public void setAndroidUpdateFlag(String androidUpdateFlag) {
        this.androidUpdateFlag = androidUpdateFlag;
    }
}
