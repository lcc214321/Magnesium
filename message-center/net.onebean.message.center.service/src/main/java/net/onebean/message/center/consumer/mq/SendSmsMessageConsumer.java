package net.onebean.message.center.consumer.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import net.onebean.core.error.BusinessException;
import net.onebean.message.center.api.model.SendSmsMsgReq;
import net.onebean.message.center.common.ErrorCodesEnum;
import net.onebean.message.center.enumModel.SendChannelEnum;
import net.onebean.message.center.service.SmsMessageSenderService;
import net.onebean.util.PropUtil;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendSmsMessageConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(SendSmsMessageConsumer.class);
    @Autowired
    private SmsMessageSenderService smsMessageSenderService;

    @RabbitListener(queues = "message.center.send.sms.message")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("SendSmsMessageConsumer process access  text = "+ text);
        try {
            if(StringUtils.isEmpty(text)){
                throw new BusinessException(ErrorCodesEnum.REQUEST_PARAM_ERROR.code(),ErrorCodesEnum.REQUEST_PARAM_ERROR.msg()+"field of mq param text");
            }
            SendSmsMsgReq req;
            try {
                req = JSON.parseObject(text,SendSmsMsgReq.class);
            } catch (Exception e) {
                LOGGER.error("SendSmsMessageConsumer process get JSON_CAST_ERROR error",e);
                throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
            }

            String sendChannel;
            try {
                sendChannel = PropUtil.getInstance().getConfig("msg.send.channel",PropUtil.DEFLAULT_NAME_SPACE);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodesEnum.READ_APOLLO_EMPTY_VALUE.code(),ErrorCodesEnum.READ_APOLLO_EMPTY_VALUE.msg()+" key of 'msg.send.channel'");
            }
            if (sendChannel.equals(SendChannelEnum.Channel_1.getKey())){
                smsMessageSenderService.sendSmsMsgByChannel1(req);
            }else {
                smsMessageSenderService.sendSmsMsgByChannel2(req);
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            LOGGER.error("SendSmsMessageConsumer process get error",e);
            try {
                //丢弃这条消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            } catch (IOException e1) {
                LOGGER.error("SendSmsMessageConsumer process get error",e1);
            }
        }
    }
}