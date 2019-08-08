package net.onebean.user.mngt.consumer.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import net.onebean.app.mngt.api.model.InitUagAccountTableConsumerReq;
import net.onebean.core.error.BusinessException;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagUserInfoService;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InitUagAccountTableConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitUagAccountTableConsumer.class);

    @Autowired
    private UagUserInfoService uagUserInfoService;

    @RabbitListener(queues = "devops.init.uag.account.table")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("InitUagAccountTableConsumer process access  text = "+ text);
        try {
            if (StringUtils.isEmpty(text)){
                LOGGER.error("process error e = text is empty");
                throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
            }
            InitUagAccountTableConsumerReq req;
            try {
                req = JSON.parseObject(text, InitUagAccountTableConsumerReq.class);
            } catch (Exception e) {
                LOGGER.error("process error e = appId is empty");
                throw new BusinessException(ErrorCodesEnum.JSON_CAST_ERROR.code(),ErrorCodesEnum.JSON_CAST_ERROR.msg());
            }
            String appId = Optional.ofNullable(req).map(InitUagAccountTableConsumerReq::getAppId).orElse("");
            if (StringUtils.isEmpty(text)){
                LOGGER.error("process error e = appId is empty");
                throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
            }
            uagUserInfoService.initUagAccountTable(appId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            LOGGER.error("process error e = ",e);
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
        }
    }
}