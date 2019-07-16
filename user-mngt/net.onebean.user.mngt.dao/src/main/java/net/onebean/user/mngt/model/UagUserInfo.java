package net.onebean.user.mngt.model;

import net.onebean.core.extend.FiledName;
import net.onebean.core.extend.TableName;
import net.onebean.core.model.BaseModel;
import net.onebean.core.model.InterfaceBaseSplitModel;

import java.sql.Timestamp;

/**
 * @author 0neBean
 * @description 用户信息 model
 * @date 2019-06-04 14:03:47
 */
@TableName("uag_user_info_")
public class UagUserInfo extends BaseModel implements InterfaceBaseSplitModel {


    /**
     * 用户名
     */
    private String username;

    @FiledName("username")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * 密码
     */
    private String password;

    @FiledName("password")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 创建时间
     */
    private Timestamp createTime;

    @FiledName("create_time")
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    /**
     * 更新时间
     */
    private Timestamp updateTime;

    @FiledName("update_time")
    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * 操作人ID
     */
    private Integer operatorId;

    @FiledName("operator_id")
    public Integer getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }


    /**
     * 操作人姓名
     */
    private String operatorName;

    @FiledName("operator_name")
    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    /**
     * 逻辑删除,0否1是
     */
    private String isDeleted;

    @FiledName("is_deleted")
    public String getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    private String isLock;

    @FiledName("is_lock")
    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }


    private String nickName;

    @FiledName("nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}