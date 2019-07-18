package net.onebean.uag.conf.consumer.mq;

import net.onebean.core.error.BusinessException;
import net.onebean.uag.conf.common.ErrorCodesEnum;
import net.onebean.uag.conf.service.AppServerService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevopsUpdateServerApiConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(DevopsUpdateServerApiConsumer.class);
    @Autowired
    private AppServerService appServerService;

    @RabbitListener(queues = "devops.update.server.or.api")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("DevopsUpdateServerApiConsumer process access  text = "+ text);
        try {
            appServerService.syncAppApiRelationship();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            LOGGER.error("DevopsUpdateServerApiConsumer get err ,e = ",e);
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
        }
    }
}