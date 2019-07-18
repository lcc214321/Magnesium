package net.onebean.user.mngt.consumer.mq;

import com.alibaba.fastjson.JSON;
import net.onebean.core.error.BusinessException;
import net.onebean.core.form.Parse;
import net.onebean.uag.log.vo.UagOperationLogMqReq;
import net.onebean.user.mngt.common.ErrorCodesEnum;
import net.onebean.user.mngt.model.UagOperatorLog;
import net.onebean.user.mngt.service.UagOperatorLogService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UagOperatorLogConsumer {

    private final static Logger LOGGER = LoggerFactory.getLogger(UagOperatorLogConsumer.class);

    @Autowired
    private UagOperatorLogService uagOperatorLogService;


    @RabbitListener(queues = "uag.cloud.control.operation.log")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("UagOperatorLogConsumer ,text = "+text);
        try {
            UagOperationLogMqReq req = JSON.parseObject(text,UagOperationLogMqReq.class);
            String description = Optional.of(req).map(UagOperationLogMqReq::getDescription).orElse("");
            String appId = Optional.of(req).map(UagOperationLogMqReq::getAppId).orElse("");
            String uagUserId = Optional.of(req).map(UagOperationLogMqReq::getUagUserId).orElse("");
            String uagUserNickName = Optional.of(req).map(UagOperationLogMqReq::getUagUserNickName).orElse("");
            UagOperatorLog uagOperatorLog = new UagOperatorLog();
            uagOperatorLog.setAppName(appId);
            uagOperatorLog.setOperatorId(Parse.toInt(uagUserId));
            uagOperatorLog.setOperatorName(uagUserNickName);
            uagOperatorLog.setOperatorDescription(description);
            uagOperatorLogService.save(uagOperatorLog);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            LOGGER.error("process error e = ",e);
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(),ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
        }
    }
}