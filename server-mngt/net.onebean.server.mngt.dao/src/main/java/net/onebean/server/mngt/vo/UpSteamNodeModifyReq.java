package net.onebean.server.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Timestamp;

public class UpSteamNodeModifyReq {

    @NotEmpty(message = "id can not be empty")
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    /**
     * 节点名称
     */
    private String nodeName;
    public String getNodeName(){
        return this.nodeName;
    }
    public void setNodeName(String nodeName){
        this.nodeName = nodeName;
    }


    /**
     * 物理地址
     */
    private String physicalPath;
    public String getPhysicalPath(){
        return this.physicalPath;
    }
    public void setPhysicalPath(String physicalPath){
        this.physicalPath = physicalPath;
    }


    /**
     * 创建时间
     */
    private Timestamp createTime;
    public Timestamp getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Timestamp createTime){
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    private Timestamp updateTime;
    public Timestamp getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Timestamp updateTime){
        this.updateTime = updateTime;
    }


    /**
     * 操作人ID
     */
    private Integer operatorId;
    public Integer getOperatorId(){
        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId){
        this.operatorId = operatorId;
    }


    /**
     * 操作人姓名
     */
    private String operatorName;
    public String getOperatorName(){
        return this.operatorName;
    }
    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }
}
