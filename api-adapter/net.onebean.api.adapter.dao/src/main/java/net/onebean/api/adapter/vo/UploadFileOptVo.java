package net.onebean.api.adapter.vo;

import java.util.Objects;


public class UploadFileOptVo {

    private static final long serialVersionUID = 3721716059117679633L;

    protected String sourceIpAddr;
    protected long fileSize;
    protected int crc32;
    private String name;
    private String groupName;
    private String remoteFileName;
    private Integer biz;
    private Integer key;
    private Long keyId;

    public UploadFileOptVo() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSourceIpAddr() {
        return sourceIpAddr;
    }

    public void setSourceIpAddr(String sourceIpAddr) {
        this.sourceIpAddr = sourceIpAddr;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getCrc32() {
        return crc32;
    }

    public void setCrc32(int crc32) {
        this.crc32 = crc32;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }

    public Integer getBiz() {
        return biz;
    }

    public void setBiz(Integer biz) {
        this.biz = biz;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadFileOptVo that = (UploadFileOptVo) o;
        return fileSize == that.fileSize &&
                crc32 == that.crc32 &&
                Objects.equals(sourceIpAddr, that.sourceIpAddr) &&
                Objects.equals(name, that.name) &&
                Objects.equals(groupName, that.groupName) &&
                Objects.equals(remoteFileName, that.remoteFileName) &&
                Objects.equals(biz, that.biz) &&
                Objects.equals(key, that.key) &&
                Objects.equals(keyId, that.keyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceIpAddr, fileSize, crc32, name, groupName, remoteFileName, biz, key, keyId);
    }

    @Override
    public String toString() {
        return "UploadFileOptVo{" +
                "sourceIpAddr='" + sourceIpAddr + '\'' +
                ", fileSize=" + fileSize +
                ", crc32=" + crc32 +
                ", name='" + name + '\'' +
                ", groupName='" + groupName + '\'' +
                ", remoteFileName='" + remoteFileName + '\'' +
                ", biz=" + biz +
                ", key=" + key +
                ", keyId=" + keyId +
                '}';
    }
}
