package net.onebean.uag.conf.consumer.mq;

import net.onebean.common.exception.BusinessException;
import net.onebean.uag.conf.common.ErrorCodesEnum;
import net.onebean.uag.conf.service.ManualUpdateServerNodeService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevopsUpdateNginxUpSteamConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(DevopsUpdateNginxUpSteamConsumer.class);
    @Autowired
    private ManualUpdateServerNodeService  manualUpdateServerNodeService;

    @RabbitListener(queues = "devops.update.nginx.upsteam.node")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("DevopsUpdateNginxUpSteamConsumer process access  text = "+ text);
        try {
            manualUpdateServerNodeService.updateAllNginxUpSteamConf();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            LOGGER.error("process error e = ",e);
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
        }
    }
}