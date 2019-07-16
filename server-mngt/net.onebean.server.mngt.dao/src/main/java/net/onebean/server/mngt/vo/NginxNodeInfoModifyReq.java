package net.onebean.server.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class NginxNodeInfoModifyReq {

    @NotEmpty(message = "id can not be empty")
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    /**
     * ip地址
     */
    private String ipAddress;
    public String getIpAddress(){
        return this.ipAddress;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    private String accessUser;
    public String getAccessUser(){
        return this.accessUser;
    }
    public void setAccessUser(String accessUser){
        this.accessUser = accessUser;
    }


    /**
     * 访问密码
     */
    private String accessPassword;
    public String getAccessPassword(){
        return this.accessPassword;
    }
    public void setAccessPassword(String accessPassword){
        this.accessPassword = accessPassword;
    }


    /**
     * 访问rsa_path
     */
    private String accessRsaPath;
    public String getAccessRsaPath(){
        return this.accessRsaPath;
    }
    public void setAccessRsaPath(String accessRsaPath){
        this.accessRsaPath = accessRsaPath;
    }


    /**
     * 访问端口号
     */
    private String accessPort;
    public String getAccessPort(){
        return this.accessPort;
    }
    public void setAccessPort(String accessPort){
        this.accessPort = accessPort;
    }


    /**
     * 访问授权方式 (0: 密码模式,1:公私钥模式)
     */
    private String accessAuthType;
    public String getAccessAuthType(){
        return this.accessAuthType;
    }
    public void setAccessAuthType(String accessAuthType){
        this.accessAuthType = accessAuthType;
    }


    /**
     * 配置文件地址
     */
    private String confPath;
    public String getConfPath(){
        return this.confPath;
    }
    public void setConfPath(String confPath){
        this.confPath = confPath;
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
