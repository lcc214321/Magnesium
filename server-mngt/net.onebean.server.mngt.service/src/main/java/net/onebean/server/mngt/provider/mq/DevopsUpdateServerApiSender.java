package net.onebean.server.mngt.provider.mq;

import net.onebean.server.mngt.common.MqQueueNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DevopsUpdateServerApiSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(DevopsUpdateServerApiSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean send(){
        this.rabbitTemplate.convertAndSend(MqQueueNameEnum.DEVOPS_UPDATE_SERVER_OR_API.getName(),"sync");
        return true;
    }



}
