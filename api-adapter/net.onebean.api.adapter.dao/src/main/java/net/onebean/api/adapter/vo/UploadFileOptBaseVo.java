package net.onebean.api.adapter.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class UploadFileOptBaseVo implements Serializable {

    protected Long id;

    @JSONField(serialize=false)
    protected Date createTime = new Date(System.currentTimeMillis());

    @JSONField(serialize=false)
    protected Date updateTime;

    public UploadFileOptBaseVo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
