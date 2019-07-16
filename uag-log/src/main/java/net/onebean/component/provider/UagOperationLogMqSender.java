package net.onebean.component.provider;

import com.alibaba.fastjson.JSON;
import net.onebean.uag.log.common.ConstantEnum;
import net.onebean.uag.log.vo.UagOperationLogMqReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UagOperationLogMqSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(UagOperationLogMqSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean send(UagOperationLogMqReq req){
        String reqJsonStr =  JSON.toJSONString(req);
        LOGGER.info("UagOperationLogMqSender send mq,req = "+reqJsonStr);
        this.rabbitTemplate.convertAndSend(ConstantEnum.UAG_CLOUD_CONTROL_OPERATION_LOG.getName(),reqJsonStr);
        return true;
    }
}
