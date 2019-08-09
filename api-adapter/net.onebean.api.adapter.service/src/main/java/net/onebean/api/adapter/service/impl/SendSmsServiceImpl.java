package net.onebean.api.adapter.service.impl;

import net.onebean.api.adapter.common.ErrorCodesEnum;
import net.onebean.api.adapter.provider.mq.SendUagLoginSmsSender;
import net.onebean.api.adapter.service.SendSmsService;
import net.onebean.api.adapter.vo.SendSalesLoginSmsReq;
import net.onebean.core.base.BaseResponse;
import net.onebean.core.error.BusinessException;
import net.onebean.core.form.Parse;
import net.onebean.uag.conf.api.model.SendLoginSmsReq;
import net.onebean.uag.conf.api.service.SendSmsCloudApi;
import net.onebean.util.PropUtil;
import net.onebean.util.StringUtils;
import net.onebean.util.UagUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SendSmsServiceImpl implements SendSmsService {

    private final static Logger logger = LoggerFactory.getLogger(SendSmsServiceImpl.class);

    @Autowired
    private SendSmsCloudApi salesSendSmsCloudApi;
    @Autowired
    private SendUagLoginSmsSender sendUagLoginSmsSender;

    private final static String UAG_LOGIN_SMS_TIME_OUT = "uag.login.sms.time.out";


    /**
     * @return 随机4位短信验证码
     */
    private static String generateVerifyCode() {
        return (int)(Math.random() * 9000 + 1000) + "";
    }

    @Override
    public Boolean sendLoginSms(SendSalesLoginSmsReq req) {
        SendLoginSmsReq cloudPram = new SendLoginSmsReq();
        String deviceToken = UagUtils.getCurrentDeviceToken();
        if (StringUtils.isEmpty(deviceToken)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of deviceToken is empty");
        }
        try {
            BeanUtils.copyProperties(cloudPram,req);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.REF_ERROR.code(),ErrorCodesEnum.REF_ERROR.msg());
        }
        try {
            Long timeOut = Parse.toLong(PropUtil.getInstance().getConfig(UAG_LOGIN_SMS_TIME_OUT,PropUtil.DEFLAULT_NAME_SPACE));
            cloudPram.setTimeOut(timeOut);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodesEnum.READ_VALUE_FROM_APOLLO.code(),ErrorCodesEnum.READ_VALUE_FROM_APOLLO.msg()+" filed of timeOut load from apollo failure");
        }
        cloudPram.setSmsCode(generateVerifyCode());
        cloudPram.setDeviceToken(deviceToken);

        /*同步回写缓存信息*/
        BaseResponse<Boolean> resp = salesSendSmsCloudApi.setLoginSmsCheckCache(cloudPram);
        Boolean flag = Optional.ofNullable(resp).map(BaseResponse::getDatas).orElse(false);
        String errMag = Optional.ofNullable(resp).map(BaseResponse::getErrMsg).orElse("");
        if (!flag){
            logger.error("call setLoginSmsCheckCache method failure , err = "+errMag);
            throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERR.code(),ErrorCodesEnum.CLOUD_API_ERR.msg()+" call setLoginSmsCheckCache method failure");
        }
        String telPhone = Optional.of(cloudPram).map(SendLoginSmsReq::getTelPhone).orElse(null);
        String smsCode = Optional.of(cloudPram).map(SendLoginSmsReq::getSmsCode).orElse(null);
        if (StringUtils.isEmpty(telPhone)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of telPhone is empty");
        }
        if (StringUtils.isEmpty(smsCode)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of telPhone is smsCode");
        }

        /*异步发送短信验证码*/
        sendUagLoginSmsSender.send(telPhone,smsCode,UagUtils.getCurrentAppId());

        return true;
    }
}
