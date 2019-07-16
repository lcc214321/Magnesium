package net.onebean.tenant.mngt.provider.mq;

import net.onebean.tenant.mngt.api.model.FindTtenantInfoVo;
import net.onebean.tenant.mngt.common.MqQueueNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DevopsInitTenantAccountSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(DevopsInitTenantAccountSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean send(FindTtenantInfoVo vo){
        this.rabbitTemplate.convertAndSend(MqQueueNameEnum.DEVOPS_TENANT_INFO_INIT.getName(),vo);
        return true;
    }



}
