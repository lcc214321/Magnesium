package net.onebean.spring.config;

import net.onebean.uag.conf.common.MqQueueNameEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqQueueDefined {

    @Bean
    public Queue devopsUpdateServerApi() {
        return new Queue(MqQueueNameEnum.DEVOPS_UPDATE_NGINX_UPSTEAM_NODE.getName());
    }

    @Bean
    public Queue devopsUpdateServerOrApi() {
        return new Queue(MqQueueNameEnum.DEVOPS_UPDATE_SERVER_OR_API.getName());
    }

    @Bean
    public Queue authSetAccessTokenCache() {
        return new Queue(MqQueueNameEnum.AUTH_SET_ACCESS_TOKEN_CACHE.getName());
    }


}
