package net.onebean.config;

import net.onebean.uag.log.common.ConstantEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UagOperationLogRabbitMqQueueDefined {


    @Bean
    public Queue uagCloudControlOperationLog() {
        return new Queue(ConstantEnum.UAG_CLOUD_CONTROL_OPERATION_LOG.getName());
    }


}
