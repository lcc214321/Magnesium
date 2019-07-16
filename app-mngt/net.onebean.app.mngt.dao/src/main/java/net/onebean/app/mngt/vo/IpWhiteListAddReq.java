package net.onebean.app.mngt.vo;

import org.hibernate.validator.constraints.NotBlank;

public class IpWhiteListAddReq {


    /**
     * ip地址
     */
    @NotBlank(message = "ipAddress can not be empty")
    private String ipAddress;
    public String getIpAddress(){
        return this.ipAddress;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
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
