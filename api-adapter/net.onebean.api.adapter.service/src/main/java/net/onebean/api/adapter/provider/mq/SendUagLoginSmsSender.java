package net.onebean.api.adapter.provider.mq;

import com.alibaba.fastjson.JSON;
import net.onebean.api.adapter.common.ErrorCodesEnum;
import net.onebean.api.adapter.common.MqQueueNameEnum;
import net.onebean.common.exception.BusinessException;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;


@Component
public class SendUagLoginSmsSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(SendUagLoginSmsSender.class);
    private final static String SMS_CONTENT = "您的验证码为{0}，请在10分钟内使用，请勿告诉他人";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean send(String telPhone,String smsCode){
        SendSmsMsgReq req = new SendSmsMsgReq();
        if (StringUtils.isEmpty(telPhone) || !StringUtils.isMobile(telPhone)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of telPhone is unLegal phone number");
        }
        if (StringUtils.isEmpty(smsCode)){
            throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+" filed of smsCode is empty");
        }
        req.setTelPhone(telPhone);
        req.setMessageBody(MessageFormat.format(SMS_CONTENT,smsCode));
        this.rabbitTemplate.convertAndSend(MqQueueNameEnum.MESSAGE_CENTER_SEND_SMS_MESSAGE.getName(), JSON.toJSONString(req));
        LOGGER.info("send message = "+req);
        return true;
    }



}
