package net.onebean.user.mngt.provider.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.onebean.user.mngt.api.model.CreateAccountMqReq;
import net.onebean.user.mngt.common.MqExchangeNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountFanOutSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(CreateAccountFanOutSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(CreateAccountMqReq req) {
        String msgString = JSON.toJSONString(req, SerializerFeature.WriteMapNullValue);
        LOGGER.info("CreateAccountFanOutSender send msg = "+msgString);
        this.rabbitTemplate.convertAndSend(MqExchangeNameEnum.UAG_CREATE_ACCOUNT_FANOUT_EXCHANGE.getName(),"", msgString);
    }

}
