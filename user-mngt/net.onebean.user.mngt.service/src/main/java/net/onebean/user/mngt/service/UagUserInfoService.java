package net.onebean.user.mngt.service;


import net.onebean.core.IBaseSplitBizManual;
import net.onebean.core.Pagination;
import net.onebean.core.extend.Sort;
import net.onebean.user.mngt.api.model.CheckUagLoginStatusReq;
import net.onebean.user.mngt.api.model.UagLoginInfo;
import net.onebean.user.mngt.model.UagUserInfo;
import net.onebean.user.mngt.vo.*;

import java.util.List;


/**
* @author 0neBean
* @description 用户信息 service
* @date 2019-06-04 14:03:47
*/

public interface UagUserInfoService extends IBaseSplitBizManual<UagUserInfo> {
    /**
     * 查找用户列表
     * @param param 参数
     * @param page 分页
     * @param sort 排序
     * @return list
     */
    List<UagUserInfoVo> findByFindUagUserInfoReq(FindUagUserInfoReq param, Pagination page, Sort sort);

    /**
     * 根据ID查找VO
     * @param param 参数
     * @return vo
     */
    UagUserInfoVo findVoById(FindUagUserInfoReq param);

    /**
     * 增加账户
     * @param param 参数
     * @return bool
     */
    Long addAccount(AddAccountReq param);

    /**
     * 更改锁定状态
     * @param request 参数
     * @return bool
     */
    Boolean toggleIsLockStatus(ToggleIsLockStatusReq request);

    /**
     * 重置密码
     * @param request 参数
     * @return bool
     */
    Boolean restPassword(ToggleIsLockStatusReq request);

    /**
     * 短信验证码登录注册
     * @param req 参数
     * @return resp
     */
    UagLoginInfo smsCodeLoginRegister(SmsCodeLoginRegisterReq req);
    /**
     * 短信验证码登录
     * @param req 参数
     * @return resp
     */
    UagLoginInfo smsCodeLogin(SmsCodeLoginRegisterReq req);
    /**
     *  账号密码登录
     * @param req 参数
     * @return resp
     */
    UagLoginInfo passwordLogin(PasswordLoginReq req);
    /**
     * 短信验证码注册
     * @param req 参数
     * @return resp
     */
    UagLoginInfo smsCodeRegister(SmsCodeLoginRegisterReq req);
    /**
     *  账号密码注册
     * @param req 参数
     * @return resp
     */
    UagLoginInfo passwordRegister(PasswordLoginReq req);
    /**
     * 检查用户登录状态
     * @param req 参数
     * @return bool
     */
    UagLoginInfo checkUagLoginStatus(CheckUagLoginStatusReq req);
    /**
     * 初始化应用对应的用户表
     * @return bool
     */
    Boolean initUagAccountTable(String appId);

    /**
     * 删除登录标识
     * @param req 参数
     * @return bool
     */
    Boolean uagLogOut(CheckUagLoginStatusReq req);

    /**
     * 修改用户信息
     * @param request 参数
     * @return bool
     */
    Boolean modify(UserInfoModifyReq request);
}