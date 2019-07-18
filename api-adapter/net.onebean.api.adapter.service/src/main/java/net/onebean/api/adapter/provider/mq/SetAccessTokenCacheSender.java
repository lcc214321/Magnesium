package net.onebean.api.adapter.provider.mq;

import com.alibaba.fastjson.JSON;
import net.onebean.api.adapter.api.model.GetAccTokenVo;
import net.onebean.api.adapter.common.MqQueueNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetAccessTokenCacheSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(SetAccessTokenCacheSender.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean send(GetAccTokenVo getAccTokenVo){
        String message = JSON.toJSONString(getAccTokenVo);
        this.rabbitTemplate.convertAndSend(MqQueueNameEnum.AUTH_SET_ACCESS_TOKEN_CACHE.getName(), message);
        LOGGER.info("send message = "+message +" to the queue "+MqQueueNameEnum.AUTH_SET_ACCESS_TOKEN_CACHE.getName());
        return true;
    }

}
