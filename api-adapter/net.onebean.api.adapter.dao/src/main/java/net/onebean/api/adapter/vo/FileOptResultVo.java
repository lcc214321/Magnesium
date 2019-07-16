package net.onebean.api.adapter.vo;

import java.util.Objects;

public class FileOptResultVo {

    private static final long serialVersionUID = 339308308497129808L;
    private UploadFileOptVo fileDO;

    public FileOptResultVo() {
    }

    public UploadFileOptVo getFileDO() {
        return fileDO;
    }

    public void setFileDO(UploadFileOptVo fileDO) {
        this.fileDO = fileDO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileOptResultVo that = (FileOptResultVo) o;
        return Objects.equals(fileDO, that.fileDO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileDO);
    }

    @Override
    public String toString() {
        return "FileOptResultVo{" +
                "fileDO=" + fileDO +
                '}';
    }
}
