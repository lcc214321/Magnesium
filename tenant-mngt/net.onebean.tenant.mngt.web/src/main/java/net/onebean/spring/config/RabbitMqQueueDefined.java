package net.onebean.spring.config;

import net.onebean.tenant.mngt.common.MqQueueNameEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqQueueDefined {


    @Bean
    public Queue devopsUpdateServerApi() {
        return new Queue(MqQueueNameEnum.DEVOPS_TENANT_INFO_INIT.getName());
    }


    @Bean
    public Queue devopsTenantInfoSyncTenantAccount() {
        return new Queue(MqQueueNameEnum.DEVOPS_TENANT_INFO_SYNC_TENANT_ACCOUNT.getName());
    }

}
