package net.onebean.spring.config;

import net.onebean.message.center.common.MqQueueNameEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqQueueDefined {

    @Bean
    public Queue messageCenterSendSmsMessage() {
        return new Queue(MqQueueNameEnum.MESSAGE_CENTER_SEND_SMS_MESSAGE.getName());
    }

}
