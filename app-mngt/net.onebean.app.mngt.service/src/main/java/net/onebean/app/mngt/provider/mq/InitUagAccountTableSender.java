package net.onebean.app.mngt.provider.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.app.mngt.api.model.InitUagAccountTableConsumerReq;
import net.onebean.app.mngt.common.MqQueueNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class InitUagAccountTableSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitUagAccountTableSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean send(InitUagAccountTableConsumerReq req){
        LOGGER.debug("InitUagAccountTableSender InitUagAccountTableConsumerReq req = ",JSON.toJSONString(req, SerializerFeature.WriteMapNullValue));
        this.rabbitTemplate.convertAndSend(MqQueueNameEnum.DEVOPS_INIT_UAG_ACCOUNT_TABLE.getName(), JSON.toJSONString(req));
        return true;
    }

}
