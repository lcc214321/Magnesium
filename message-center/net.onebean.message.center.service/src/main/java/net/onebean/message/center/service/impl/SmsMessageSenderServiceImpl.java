package net.onebean.message.center.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.onebean.common.exception.BusinessException;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.message.center.common.ErrorCodesEnum;
import net.onebean.message.center.common.MsgypeEnum;
import net.onebean.message.center.model.MsgCodeRecord;
import net.onebean.message.center.service.MsgCodeRecordService;
import net.onebean.message.center.service.SmsMessageSenderService;
import net.onebean.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Optional;

@Service
public class SmsMessageSenderServiceImpl implements SmsMessageSenderService {

    private final static Logger logger = LoggerFactory.getLogger(SmsMessageSenderServiceImpl.class);
    private final static String CHANNEL_2_SEND_TYPE = "0";

    private final static String SEND_SMS_API_BASE_URL = "send.sms.api.base.url";
    private final static String SEND_SMS_API_SIGN = "send.sms.api.sign";
    private final static String SEND_SMS_ACCOUNT = "send.sms.account";
    private final static String SEND_SMS_PASSWORD = "send.sms.password";
    @Autowired
    private MsgCodeRecordService msgCodeRecordService;

    @Override
    public Boolean sendSmsMsgByChannel1(SendSmsMsgReq req) {
        String telPhone = Optional.ofNullable(req).map(SendSmsMsgReq::getTelPhone).orElse(null);
        String messageBody = Optional.ofNullable(req).map(SendSmsMsgReq::getMessageBody).orElse(null);
        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(messageBody)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }

        saveSendRecord(req);
        String successFlag = "提交成功";
        String sendSmsApiBaseUrl = PropUtil.getInstance().getConfig(SEND_SMS_API_BASE_URL,PropUtil.DEFLAULT_NAME_SPACE);
        String sendSmsAccount = PropUtil.getInstance().getConfig(SEND_SMS_ACCOUNT,PropUtil.DEFLAULT_NAME_SPACE);;
        String sendSmsPassword = PropUtil.getInstance().getConfig(SEND_SMS_PASSWORD,PropUtil.DEFLAULT_NAME_SPACE);;
        String sendSmsApiSign = PropUtil.getInstance().getConfig(SEND_SMS_API_SIGN,PropUtil.DEFLAULT_NAME_SPACE);
        String url = sendSmsApiBaseUrl+"name={0}&pwd={1}&mobile={2}&content={3}&stime={4}&sign={5}&type=pt&extno={6}";
        url = MessageFormat.format(url, sendSmsAccount,sendSmsPassword,telPhone,messageBody,StringUtils.EMPTY,sendSmsApiSign,StringUtils.EMPTY);
        String res = RestUtils.getInstance().doPostForObj(url,null,String.class).toString();
        logger.info("send message to "+telPhone+" res = "+res);
        if (!res.contains(successFlag)){
            throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERROR.code(),ErrorCodesEnum.CLOUD_API_ERROR.msg());
        }
        return true;
    }



    @Override
    public Boolean sendSmsMsgByChannel2(SendSmsMsgReq req) {
        String sendSmsApiBaseUrl = PropUtil.getInstance().getConfig("channel2.send.url",PropUtil.DEFLAULT_NAME_SPACE);
        String sysName = PropUtil.getInstance().getConfig("channel2.sys.name",PropUtil.DEFLAULT_NAME_SPACE);
        String secret = PropUtil.getInstance().getConfig("channel2.secret",PropUtil.DEFLAULT_NAME_SPACE);

        String telPhone = Optional.ofNullable(req).map(SendSmsMsgReq::getTelPhone).orElse(null);
        String messageBody = Optional.ofNullable(req).map(SendSmsMsgReq::getMessageBody).orElse(null);
        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(messageBody)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(), ErrorCodesEnum.REQUEST_PARAM_ERROR.msg());
        }

        saveSendRecord(req);
        String token = EncryptionUtils.md5Hex(EncryptionUtils.md5Hex(sysName + secret).toLowerCase() + DateUtils.getNowYyyy_MM_dd());
        try {
            messageBody = URLEncoder.encode(messageBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ErrorCodesEnum.URL_ENCODE_ERR.code(),ErrorCodesEnum.URL_ENCODE_ERR.msg());
        }

        String successFlag = "success";
        String param = sendSmsApiBaseUrl+"message={0}&sysName={1}&type={2}&phones={3}&token={4}";
        param = MessageFormat.format(param,messageBody,sysName,CHANNEL_2_SEND_TYPE,telPhone,token);
        String res = RestUtils.getInstance().sendPostByUrlParam(sendSmsApiBaseUrl, param);
        if (!res.contains(successFlag)){
            throw new BusinessException(ErrorCodesEnum.CLOUD_API_ERROR.code(),ErrorCodesEnum.CLOUD_API_ERROR.msg());
        }
        logger.info("send message to "+telPhone+" res = "+res);
        return true;
    }

    /*短信发送成功后保存记录*/
    private void saveSendRecord(SendSmsMsgReq req){
        String telPhone = Optional.ofNullable(req).map(SendSmsMsgReq::getTelPhone).orElse(null);
        String messageBody = Optional.ofNullable(req).map(SendSmsMsgReq::getMessageBody).orElse(null);
        try {
            MsgCodeRecord msgCodeRecord = new MsgCodeRecord();
            msgCodeRecord.setMsgCode(messageBody);
            msgCodeRecord.setMsgType(MsgypeEnum.MSG_TYPE_MSG.code());
            msgCodeRecord.setTelphone(telPhone);
            msgCodeRecordService.save(msgCodeRecord);
        }catch (Exception e){
            throw new BusinessException(ErrorCodesEnum.INSERT_MSG_DATA_ERR.code(),ErrorCodesEnum.INSERT_MSG_DATA_ERR.msg());
        }
    }
}
