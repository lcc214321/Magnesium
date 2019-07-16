package net.onebean.server.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class UpsteamNameAddReq {


    /**
     * 节点名称
     */
    @NotEmpty(message = "upsteamName can not be empty")
    private String upsteamName;
    public String getUpsteamName(){
        return this.upsteamName;
    }
    public void setUpsteamName(String upsteamName){
        this.upsteamName = upsteamName;
    }



    private Integer operatorId;
    public Integer getOperatorId(){
        return this.operatorId;
    }
    public void setOperatorId(Integer operatorId){
        this.operatorId = operatorId;
    }



    private String operatorName;
    public String getOperatorName(){
        return this.operatorName;
    }
    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }
}
