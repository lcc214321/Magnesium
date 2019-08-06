package net.onebean.spring.config;

import net.onebean.user.mngt.common.MqQueueNameEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqQueueDefined {

    @Bean
    public Queue uagCloudControlOperationLog() {
        return new Queue(MqQueueNameEnum.UAG_CLOUD_CONTROL_OPERATION_LOG.getName());
    }
    @Bean
    public Queue uagUserAccountResetPassword() {
        return new Queue(MqQueueNameEnum.UAG_USER_ACCOUNT_RESET_PASSWORD.getName());
    }

}
