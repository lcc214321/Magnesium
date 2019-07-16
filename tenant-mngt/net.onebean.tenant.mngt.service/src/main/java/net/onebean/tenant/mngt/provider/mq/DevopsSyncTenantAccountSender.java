package net.onebean.tenant.mngt.provider.mq;

import net.onebean.tenant.mngt.api.model.TenantInfoSyncVo;
import net.onebean.tenant.mngt.common.MqQueueNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DevopsSyncTenantAccountSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(DevopsSyncTenantAccountSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Boolean send(List<TenantInfoSyncVo> unSync){
        this.rabbitTemplate.convertAndSend(MqQueueNameEnum.DEVOPS_TENANT_INFO_SYNC_TENANT_ACCOUNT.getName(),unSync);
        return true;
    }



}
