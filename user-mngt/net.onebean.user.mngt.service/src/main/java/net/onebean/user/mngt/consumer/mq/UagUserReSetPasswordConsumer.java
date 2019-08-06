package net.onebean.user.mngt.consumer.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import net.onebean.core.error.BusinessException;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.service.UagUserInfoService;
import net.onebean.user.mngt.vo.ToggleIsLockStatusReq;
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
public class UagUserReSetPasswordConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(UagUserReSetPasswordConsumer.class);

    @Autowired
    private UagUserInfoService uagUserInfoService;


    @RabbitListener(queues = "uag.user.account.reset.password")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("UagUserReSetPasswordConsumer ,text = "+text);
        try {
            ToggleIsLockStatusReq req = JSON.parseObject(text, ToggleIsLockStatusReq.class);
            String appId = Optional.of(req).map(ToggleIsLockStatusReq::getAppId).orElse("");
            String uagUserId = Optional.of(req).map(ToggleIsLockStatusReq::getUserId).orElse("");
            if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(uagUserId)){
                throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg()+" appId or uagUserId is empty");
            }
            uagUserInfoService.restPassword(req);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            LOGGER.error("process error e = ",e);
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
        }
    }
}