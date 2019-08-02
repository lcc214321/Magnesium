package net.onebean.user.mngt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.app.mngt.api.model.AppInfoSyncVo;
import net.onebean.component.PasswordEncoder;
import net.onebean.component.redis.IRedisService;
import net.onebean.core.base.BaseSplitBizManual;
import net.onebean.core.error.BusinessException;
import net.onebean.core.extend.Sort;
import net.onebean.core.form.Parse;
import net.onebean.core.query.Condition;
import net.onebean.core.query.Pagination;
import net.onebean.uag.conf.api.model.SendLoginSmsReq;
import net.onebean.user.mngt.api.model.CheckUagLoginStatusReq;
import net.onebean.user.mngt.api.model.CreateAccountMqReq;
import net.onebean.user.mngt.api.model.UagLoginInfo;
import net.onebean.user.mngt.api.model.enumModel.PrivateTokenLoginFlagEnum;
import net.onebean.user.mngt.common.CacheConstants;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.dao.UagUserInfoDao;
import net.onebean.user.mngt.enumModel.IslockStatusEnum;
import net.onebean.user.mngt.model.UagUserInfo;
import net.onebean.user.mngt.provider.mq.CreateAccountFanOutSender;
import net.onebean.user.mngt.service.AppCacheInfoService;
import net.onebean.user.mngt.service.UagUserInfoService;
import net.onebean.user.mngt.vo.*;
import net.onebean.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 0neBean
 * @description 用户信息 serviceImpl
 * @date 2019-06-04 14:03:47
 */
@Service
public class UagUserInfoServiceImpl extends BaseSplitBizManual<UagUserInfo, UagUserInfoDao> implements UagUserInfoService {


    private final static Logger logger = LoggerFactory.getLogger(UagUserInfoServiceImpl.class);
    private final static String DEFAULT_PASSWORD = "123456";
    private final static String TEMPLATE_FILE_PATH = "/account/initUagAccountTable.ftl";
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private AppCacheInfoService appCacheInfoService;
    @Autowired
    private CreateAccountFanOutSender createAccountFanOutSender;


    @Override
    public List<UagUserInfoVo> findByFindUagUserInfoReq(FindUagUserInfoReq param, Pagination page, Sort sort) {

        String id = Optional.ofNullable(param).map(FindUagUserInfoReq::getId).orElse("");
        String appId = Optional.ofNullable(param).map(FindUagUserInfoReq::getAppId).orElse("");
        String username = Optional.ofNullable(param).map(FindUagUserInfoReq::getUsername).orElse("");
        String password = Optional.ofNullable(param).map(FindUagUserInfoReq::getPassword).orElse("");


        List<Condition> paramList = new ArrayList<>();
        if (StringUtils.isNotEmpty(username)) {
            Condition condition = Condition.parseModelCondition("username@string@like");
            condition.setValue(username);
            paramList.add(condition);
        }
        if (StringUtils.isNotEmpty(password)) {
            Condition condition = Condition.parseModelCondition("password@string@eq");
            condition.setValue(password);
            paramList.add(condition);
        }

        if (StringUtils.isNotEmpty(id)) {
            Condition condition = Condition.parseModelCondition("id@int@eq");
            condition.setValue(id);
            paramList.add(condition);
        }


        List<UagUserInfo> list = this.find(page, paramList, sort, appId);
        logger.debug("UagUserInfoServiceImpl method findByFindUagUserInfoReq list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<UagUserInfoVo> res = JSON.parseArray(JSON.toJSONString(list), UagUserInfoVo.class);
        logger.debug("UagUserInfoServiceImpl method findByFindUagUserInfoReq res = " + JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(res)) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg());
        }
        return res;
    }

    @Override
    public UagUserInfoVo findVoById(FindUagUserInfoReq param) {
        Object id = Optional.ofNullable(param).map(FindUagUserInfoReq::getId).orElse("");
        String appId = Optional.ofNullable(param).map(FindUagUserInfoReq::getAppId).orElse("");

        if (StringUtils.isEmpty(id)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg() + " param of id");
        }
        UagUserInfo uagUserInfo = this.findById(id, appId);
        UagUserInfoVo uagUserInfoVo = new UagUserInfoVo();
        if (null != uagUserInfo) {
            try {
                BeanUtils.copyProperties(uagUserInfoVo, uagUserInfo);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg());
            }
        } else {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        return uagUserInfoVo;
    }

    @Override
    public Long addAccount(AddAccountReq param) {
        String appId = Optional.ofNullable(param).map(AddAccountReq::getAppId).orElse("");
        String username = Optional.ofNullable(param).map(AddAccountReq::getUsername).orElse("");
        String password = Optional.ofNullable(param).map(AddAccountReq::getPassword).orElse("");
        if (StringUtils.isEmpty(password)) {
            password = DEFAULT_PASSWORD;
        }
        password = passwordEncoder.encode(password);


        UagUserInfo uagUserInfo = new UagUserInfo();
        try {
            BeanUtils.copyProperties(uagUserInfo, param);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(), ErrorCodesEnum.REF_ERROR.msg());
        }
        uagUserInfo.setPassword(password);
        Condition condition = Condition.parseModelCondition("username@string@eq");
        condition.setValue(username);

        if (CollectionUtil.isNotEmpty(this.find(null, condition, appId))) {
            throw new BusinessException(ErrorCodesEnum.DATA_REPEAT_ERR.code(), ErrorCodesEnum.DATA_REPEAT_ERR.msg() + " account repeat");
        }
        try {
            UagSsoUtils.setUagUserInfoByHeader(uagUserInfo);
            this.save(uagUserInfo, appId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.INSERT_DATA_ERROR.code(), ErrorCodesEnum.INSERT_DATA_ERROR.msg() + " param = " + JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        }
        Long uagUserId = Optional.of(uagUserInfo).map(UagUserInfo::getId).orElse(0L);
        if (uagUserId <= 0) {
            throw new BusinessException(ErrorCodesEnum.INSERT_DATA_ERROR.code(), ErrorCodesEnum.INSERT_DATA_ERROR.msg() + " param = " + JSON.toJSONString(param, SerializerFeature.WriteMapNullValue));
        }
        CreateAccountMqReq req = new CreateAccountMqReq(uagUserId.toString(), username);
        createAccountFanOutSender.send(req);
        return uagUserId;
    }


    @Override
    public Boolean toggleIsLockStatus(ToggleIsLockStatusReq param) {
        String appId = Optional.ofNullable(param).map(ToggleIsLockStatusReq::getAppId).orElse("");
        String userId = Optional.ofNullable(param).map(ToggleIsLockStatusReq::getUserId).orElse("");

        UagUserInfo uagUserInfo = this.findById(userId, appId);
        if (StringUtils.isEmpty(uagUserInfo)) {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg() + " userId is invalid");
        }

        String isLock = Optional.of(uagUserInfo).map(UagUserInfo::getIsLock).orElse("");

        if (StringUtils.isEmpty(isLock)) {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg() + " isLock is empty");
        }

        isLock = isLock.equals(IslockStatusEnum.UN_LOCK.getKey()) ? IslockStatusEnum.LOCKED.getKey() : IslockStatusEnum.UN_LOCK.getKey();
        uagUserInfo.setIsLock(isLock);
        UagSsoUtils.setUagUserInfoByHeader(uagUserInfo);
        this.save(uagUserInfo, appId);

        return true;
    }

    @Override
    public Boolean restPassword(ToggleIsLockStatusReq param) {
        String appId = Optional.ofNullable(param).map(ToggleIsLockStatusReq::getAppId).orElse("");
        String userId = Optional.ofNullable(param).map(ToggleIsLockStatusReq::getUserId).orElse("");

        UagUserInfo uagUserInfo = this.findById(userId, appId);
        if (StringUtils.isEmpty(uagUserInfo)) {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg() + " userId is invalid");
        }

        String password = passwordEncoder.encode(DEFAULT_PASSWORD);
        uagUserInfo.setPassword(password);
        UagSsoUtils.setUagUserInfoByHeader(uagUserInfo);
        this.save(uagUserInfo, appId);

        return true;
    }

    @Override
    public UagLoginInfo smsCodeLoginRegister(SmsCodeLoginRegisterReq param) {
        String telPhone = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getTelPhone).orElse("");
        String smsCode = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getSmsCode).orElse("");
        String deviceToken = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getDeviceToken).orElse("");

        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(smsCode) || StringUtils.isEmpty(deviceToken)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_SMS_CODE.code(), ErrorCodesEnum.INVALID_SMS_CODE.msg());
        }
        /*校验短信验证码*/
        checkSmsCode(telPhone, smsCode, deviceToken);
        /*执行登录注册逻辑*/
        UagLoginInfo loginRegisterResp = doSigInRegister(telPhone);
        /*设置登录标识*/
        setLoginFlag(loginRegisterResp, telPhone, deviceToken);
        /*如无异常中断 返回resp*/
        return setOauthBaseUrl(loginRegisterResp);
    }

    @Override
    public UagLoginInfo smsCodeLogin(SmsCodeLoginRegisterReq param) {
        String telPhone = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getTelPhone).orElse("");
        String smsCode = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getSmsCode).orElse("");
        String deviceToken = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getDeviceToken).orElse("");

        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(smsCode) || StringUtils.isEmpty(deviceToken)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_SMS_CODE.code(), ErrorCodesEnum.INVALID_SMS_CODE.msg());
        }
        /*校验短信验证码*/
        checkSmsCode(telPhone, smsCode, deviceToken);
        /*执行登录逻辑*/
        UagLoginInfo loginRegisterResp = doLogin(telPhone);
        /*设置登录标识*/
        setLoginFlag(loginRegisterResp, telPhone, deviceToken);
        /*如无异常中断 返回resp*/
        return setOauthBaseUrl(loginRegisterResp);
    }

    @Override
    public UagLoginInfo smsCodeRegister(SmsCodeLoginRegisterReq param) {
        String telPhone = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getTelPhone).orElse("");
        String smsCode = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getSmsCode).orElse("");
        String deviceToken = Optional.ofNullable(param).map(SmsCodeLoginRegisterReq::getDeviceToken).orElse("");

        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(smsCode) || StringUtils.isEmpty(deviceToken)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_SMS_CODE.code(), ErrorCodesEnum.INVALID_SMS_CODE.msg());
        }
        /*校验短信验证码*/
        checkSmsCode(telPhone, smsCode, deviceToken);
        /*如无异常中断 返回resp*/
        return doRegister(telPhone, DEFAULT_PASSWORD);
    }

    /*执行登录注册逻辑*/
    private UagLoginInfo doLogin(String telPhone) {
        String appId = UagUtils.getCurrentAppId();
        String loginStatusComment = PrivateTokenLoginFlagEnum.getLoginStatusComment();
        Condition param1 = Condition.parseModelCondition("username@string@eq");
        param1.setValue(telPhone);
        Condition param2 = Condition.parseModelCondition("isLock@string@eq");
        param2.setValue(IslockStatusEnum.UN_LOCK.getKey());
        List<Condition> params = new ArrayList<>(Arrays.asList(param1, param2));

        List<UagUserInfo> uagUserInfoList = this.find(null, params, appId);
        if (CollectionUtil.isEmpty(uagUserInfoList)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_ACCOUNT.code(), ErrorCodesEnum.INVALID_ACCOUNT.msg());
        }
        Long uagUserId = Optional.of(uagUserInfoList).map(l -> l.get(0)).map(UagUserInfo::getId).map((id) -> Parse.toLong(id)).orElse(0L);
        String nickName = Optional.of(uagUserInfoList).map(l -> l.get(0)).map(UagUserInfo::getNickName).orElse("");
        String loginStatus = PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN.getKey();
        //return login Success
        UagLoginInfo resp = new UagLoginInfo();
        resp.setUagUserId(uagUserId.toString());
        resp.setLoginStatus(loginStatus);
        resp.setUagUserNickName(nickName);
        resp.setUagUsername(telPhone);
        resp.setLoginStatusComment(loginStatusComment);
        return resp;
    }

    /*执行登录注册逻辑*/
    private UagLoginInfo doRegister(String telPhone, String password) {
        String appId = UagUtils.getCurrentAppId();
        String loginStatus;
        String loginStatusComment = PrivateTokenLoginFlagEnum.getLoginStatusComment();
        Long uagUserId;
        Condition condition = Condition.parseModelCondition("username@string@eq");
        condition.setValue(telPhone);
        List<UagUserInfo> uagUserInfoList = this.find(null, condition, appId);
        if (CollectionUtil.isEmpty(uagUserInfoList)) {
            AddAccountReq param = new AddAccountReq();
            param.setAppId(appId);
            param.setUsername(telPhone);
            param.setPassword(password);
            uagUserId = this.addAccount(param);
            loginStatus = PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_REGISTER.getKey();
        } else {
            throw new BusinessException(ErrorCodesEnum.REPEAT_DATA_ERR.code(), ErrorCodesEnum.REPEAT_DATA_ERR.msg());
        }
        //return login Success
        UagLoginInfo resp = new UagLoginInfo();
        resp.setUagUserId(uagUserId.toString());
        resp.setLoginStatus(loginStatus);
        resp.setUagUsername(telPhone);
        resp.setLoginStatusComment(loginStatusComment);
        return resp;
    }

    /*执行登录注册逻辑*/
    private UagLoginInfo doSigInRegister(String telPhone) {
        String appId = UagUtils.getCurrentAppId();
        String loginStatus;
        String loginStatusComment = PrivateTokenLoginFlagEnum.getLoginStatusComment();
        Long uagUserId;

        Condition condition = Condition.parseModelCondition("username@string@eq");
        condition.setValue(telPhone);
        List<UagUserInfo> uagUserInfoList = this.find(null, condition, appId);
        if (CollectionUtil.isEmpty(uagUserInfoList)) {
            AddAccountReq param = new AddAccountReq();
            param.setAppId(appId);
            param.setUsername(telPhone);
            uagUserId = this.addAccount(param);
            loginStatus = PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_REGISTER.getKey();
        } else {
            uagUserId = Optional.of(uagUserInfoList).map(l -> l.get(0)).map(UagUserInfo::getId).map((id) -> Parse.toLong(id)).orElse(0L);
            loginStatus = PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN.getKey();
        }
        //return login Success
        UagLoginInfo resp = new UagLoginInfo();
        resp.setUagUserId(uagUserId.toString());
        resp.setLoginStatus(loginStatus);
        resp.setUagUsername(telPhone);
        resp.setLoginStatusComment(loginStatusComment);
        return resp;
    }


    /*设置登录标识*/
    private void setLoginFlag(UagLoginInfo loginRegisterResp, String telPhone, String deviceToken) {
        String appId = UagUtils.getCurrentAppId();
        //设置账号为key的登录标识
        String redisAccountKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_LOGIN_FLAG_ACCOUNT_KEY.getValue(), appId);
        //设置设备号为key的登录标识
        String redisDeviceTokenKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_LOGIN_FLAG_DEVICETOKEN_KEY.getValue(), appId);

        String oldDeviceToken = Optional.ofNullable(iRedisService.hGet(redisAccountKey, telPhone)).map(s -> s + "").orElse("");
        //检查手机号是否已登录,如有先删除登录标识
        if (StringUtils.isNotEmpty(oldDeviceToken)) {
            iRedisService.hDel(redisAccountKey, telPhone);
            iRedisService.hDel(redisDeviceTokenKey, oldDeviceToken);
        }

        JSONObject accountTable = new JSONObject();
        JSONObject deviceTokenTable = new JSONObject();
        accountTable.put(telPhone, deviceToken);
        deviceTokenTable.put(deviceToken, JSON.toJSONString(loginRegisterResp));

        iRedisService.hSetAll(redisDeviceTokenKey, JSONUtil.toMap(JSONUtil.toJson(deviceTokenTable)));
        iRedisService.hSetAll(redisAccountKey, JSONUtil.toMap(JSONUtil.toJson(accountTable)));
    }

    @Override
    public Boolean uagLogOut(CheckUagLoginStatusReq req) {
        String appId = Optional.of(req).map(CheckUagLoginStatusReq::getAppId).orElse("");
        String deviceToken = Optional.of(req).map(CheckUagLoginStatusReq::getDeviceToken).orElse("");
        UagLoginInfo uagLoginInfo = checkUagLoginStatus(req);
        String uagUsername = Optional.of(uagLoginInfo).map(UagLoginInfo::getUagUsername).orElse("");
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(deviceToken) || StringUtils.isEmpty(uagUsername)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }

        //设置账号为key的登录标识
        String redisAccountKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_LOGIN_FLAG_ACCOUNT_KEY.getValue(), appId);
        //设置设备号为key的登录标识
        String redisDeviceTokenKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_LOGIN_FLAG_DEVICETOKEN_KEY.getValue(), appId);
        try {
            iRedisService.hDel(redisAccountKey, uagUsername);
            iRedisService.hDel(redisDeviceTokenKey, deviceToken);
            logger.info("did deleted login flag uagUsername,redis key " + redisAccountKey + "hkey = " + uagUsername);
            logger.info("did deleted login flag deviceToken,redis key " + redisDeviceTokenKey + "hkey = " + deviceToken);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.DELETE_CACHE_ERR.code(), ErrorCodesEnum.DELETE_CACHE_ERR.msg());
        }
        return true;
    }


    /*校验短信验证码合法性*/
    private void checkSmsCode(String telPhone, String smsCode, String deviceToken) {
        String redisKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.LOGIN_SMS_CODE.getValue(), telPhone);

        logger.info("checkSmsCode start redisKey is " + redisKey);
        String smsCodeJsonStr = iRedisService.get(redisKey);

        if (StringUtils.isEmpty(smsCodeJsonStr)) {
            logger.error("smsCodeJsonStr is empty");
            throw new BusinessException(ErrorCodesEnum.INVALID_SMS_CODE.code(), ErrorCodesEnum.INVALID_SMS_CODE.msg());
        }

        SendLoginSmsReq smsCodeJson;
        try {
            smsCodeJson = JSON.parseObject(smsCodeJsonStr, SendLoginSmsReq.class);
        } catch (Exception e) {
            logger.error("parse smsCodeJson err,smsCodeJsonStr = " + smsCodeJsonStr);
            throw new BusinessException(ErrorCodesEnum.INVALID_SMS_CODE.code(), ErrorCodesEnum.INVALID_SMS_CODE.msg());
        }

        String deviceTokenCache = Optional.ofNullable(smsCodeJson).map(SendLoginSmsReq::getDeviceToken).orElse("");
        String smsCodeCache = Optional.ofNullable(smsCodeJson).map(SendLoginSmsReq::getSmsCode).orElse("");


        if (StringUtils.isEmpty(deviceTokenCache) || StringUtils.isEmpty(smsCodeCache)) {
            logger.error("deviceTokenCache or smsCodeCache  is empty");
            throw new BusinessException(ErrorCodesEnum.INVALID_SMS_CODE.code(), ErrorCodesEnum.INVALID_SMS_CODE.msg());
        }

        if (!smsCode.equals(smsCodeCache) || !deviceToken.equals(deviceTokenCache)) {
            logger.error("smsCodeCache or deviceTokenCache not equals cache value");
            throw new BusinessException(ErrorCodesEnum.INVALID_SMS_CODE.code(), ErrorCodesEnum.INVALID_SMS_CODE.msg());
        }

        iRedisService.del(redisKey);
        logger.info("did delete smsCode , key = " + redisKey);
    }

    @Override
    public UagLoginInfo passwordLogin(PasswordLoginReq req) {
        String appId = UagUtils.getCurrentAppId();
        String telPhone = Optional.ofNullable(req).map(PasswordLoginReq::getTelPhone).orElse("");
        String password = Optional.ofNullable(req).map(PasswordLoginReq::getPassword).orElse("");
        String deviceToken = Optional.ofNullable(req).map(PasswordLoginReq::getDeviceToken).orElse("");
        /*执行密码登录逻辑*/
        UagLoginInfo loginRegisterResp = doPasswordLogin(appId, telPhone, password);
        /*设置登录标识*/
        setLoginFlag(loginRegisterResp, telPhone, deviceToken);
        /*如无异常中断 返回resp*/
        return setOauthBaseUrl(loginRegisterResp);
    }

    private UagLoginInfo setOauthBaseUrl(UagLoginInfo loginRegisterResp) {
        String appId = UagUtils.getCurrentAppId();
        AppInfoSyncVo appInfoSyncVo = appCacheInfoService.getAppInfoFromCache(appId);
        String oauthBaseUrl = Optional.ofNullable(appInfoSyncVo).map(AppInfoSyncVo::getOauthBaseUrl).orElse("");
        loginRegisterResp.setOauthBaseUrl(oauthBaseUrl);
        return loginRegisterResp;
    }

    private UagLoginInfo doPasswordLogin(String appId, String telPhone, String password) {
        List<Condition> paramList = new ArrayList<>();
        if (StringUtils.isNotEmpty(telPhone)) {
            Condition condition = Condition.parseModelCondition("username@string@eq");
            condition.setValue(telPhone);
            paramList.add(condition);
        }
        Condition param2 = Condition.parseModelCondition("isLock@string@eq");
        param2.setValue(IslockStatusEnum.UN_LOCK.getKey());
        paramList.add(param2);

        List<UagUserInfo> list = this.find(null, paramList, appId);
        logger.debug("UagUserInfoServiceImpl method findByFindUagUserInfoReq list = " + JSON.toJSONString(list, SerializerFeature.WriteMapNullValue));
        if (CollectionUtil.isEmpty(list)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_ACCOUNT.code(), ErrorCodesEnum.INVALID_ACCOUNT.msg());
        }
        String uagUserId = Optional.of(list).map(l -> list.get(0)).map(UagUserInfo::getId).map(id -> id + "").orElse("");
        String nickName = Optional.of(list).map(l -> list.get(0)).map(UagUserInfo::getNickName).orElse("");
        String passwordRaw = Optional.of(list).map(l -> list.get(0)).map(UagUserInfo::getPassword).map(id -> id + "").orElse("");

        if (StringUtils.isEmpty(uagUserId)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_ACCOUNT.code(), ErrorCodesEnum.INVALID_ACCOUNT.msg());
        }

        if (!passwordEncoder.matches(password, passwordRaw)) {
            throw new BusinessException(ErrorCodesEnum.INVALID_PASSWORD.code(), ErrorCodesEnum.INVALID_PASSWORD.msg());
        }

        UagLoginInfo resp = new UagLoginInfo();
        resp.setUagUserId(uagUserId);
        resp.setUagUserNickName(nickName);
        resp.setUagUsername(telPhone);
        resp.setLoginStatusComment(PrivateTokenLoginFlagEnum.getLoginStatusComment());
        resp.setLoginStatus(PrivateTokenLoginFlagEnum.OAUTH_PRIVATE_TOKEN_LOGIN_FLAG_LOGIN.getKey());
        return resp;
    }

    @Override
    public UagLoginInfo passwordRegister(PasswordLoginReq param) {
        String telPhone = Optional.ofNullable(param).map(PasswordLoginReq::getTelPhone).orElse("");
        String password = Optional.ofNullable(param).map(PasswordLoginReq::getPassword).orElse("");
        String deviceToken = Optional.ofNullable(param).map(PasswordLoginReq::getDeviceToken).orElse("");

        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(deviceToken)) {
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }
        /*如无异常中断 返回resp*/
        return doRegister(telPhone, password);
    }

    @Override
    public UagLoginInfo checkUagLoginStatus(CheckUagLoginStatusReq param) {
        String appId = Optional.ofNullable(param).map(CheckUagLoginStatusReq::getAppId).orElse("");
        String deviceToken = Optional.ofNullable(param).map(CheckUagLoginStatusReq::getDeviceToken).orElse("");
        String redisKey = CacheConstants.generateKey(CacheConstants.UagScopeKeys.UAG_LOGIN_FLAG_DEVICETOKEN_KEY.getValue(), appId);
        String deviceTokenJsonStr = Optional.ofNullable(iRedisService.hGet(redisKey, deviceToken)).map(s -> s + "").orElse("");
        if (StringUtils.isEmpty(deviceTokenJsonStr)) {
            throw new BusinessException(ErrorCodesEnum.GET_CACHE_ERR.code(), ErrorCodesEnum.GET_CACHE_ERR.msg() + "redisKey = " + redisKey);
        }
        UagLoginInfo resp;
        try {
            resp = JSONUtil.toBean(deviceTokenJsonStr, UagLoginInfo.class);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(), ErrorCodesEnum.JSON_CAST_ERROR.msg() + "deviceTokenJsonStr = " + deviceTokenJsonStr);
        }
        return resp;
    }


    @Override
    public Boolean initUagAccountTable(String appId) {
        try {
            String sql = mergeTemplate(appId);
            baseDao.InitUagAccountTable(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 根据生成模板生成sql语句
     * return sql
     */
    private String mergeTemplate(String appId) {
        JSONObject param = new JSONObject();
        param.put("appId", appId);
        return FreeMarkerTemplateUtils.generateStringFromFreeMarker(param, TEMPLATE_FILE_PATH);
    }

    @Override
    public Boolean modify(UserInfoModifyReq request) {
        String nickName = Optional.ofNullable(request).map(UserInfoModifyReq::getNickName).orElse("");
        String id = Optional.ofNullable(request).map(UserInfoModifyReq::getId).orElse("");
        String appId = Optional.ofNullable(request).map(UserInfoModifyReq::getAppId).orElse("");

        UagUserInfo userInfo = this.findById(id, appId);
        if (null == userInfo) {
            throw new BusinessException(ErrorCodesEnum.NONE_QUERY_DATA.code(), ErrorCodesEnum.NONE_QUERY_DATA.msg());
        }
        userInfo.setNickName(nickName);
        try {
            UagSsoUtils.setUagUserInfoByHeader(userInfo);
            this.save(userInfo, appId);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.INSERT_DATA_ERROR.code(), ErrorCodesEnum.INSERT_DATA_ERROR.msg());
        }

        return true;
    }
}