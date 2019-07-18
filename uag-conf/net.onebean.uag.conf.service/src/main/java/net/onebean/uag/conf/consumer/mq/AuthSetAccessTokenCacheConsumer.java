package net.onebean.uag.conf.consumer.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import net.onebean.api.adapter.api.model.GetAccTokenVo;
import net.onebean.core.error.BusinessException;
import net.onebean.component.redis.IRedisService;
import net.onebean.core.form.Parse;
import net.onebean.uag.conf.common.CacheConstants;
import net.onebean.uag.conf.common.ErrorCodesEnum;
import net.onebean.util.ApolloPropUtils;
import net.onebean.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthSetAccessTokenCacheConsumer {


    private final static Logger LOGGER = LoggerFactory.getLogger(AuthSetAccessTokenCacheConsumer.class);
    @Autowired
    private IRedisService redisService;


    @RabbitListener(queues = "auth.set.access.token.cache")
    @RabbitHandler
    public void process(String text, Channel channel, Message message) {
        LOGGER.info("AuthSetAccessTokenCacheConsumer process access  text = " + text);

        try {
            GetAccTokenVo getAccTokenVo = JSON.parseObject(text, GetAccTokenVo.class);
            String appId = Optional.of(getAccTokenVo).map(GetAccTokenVo::getAppId).orElse("");
            String accessToken_expire = Optional.of(getAccTokenVo).map(GetAccTokenVo::getAccessTokenExpire).orElse("");
            String accessTokenCacheKey = Optional.of(getAccTokenVo).map(GetAccTokenVo::getAccessTokenCacheKey).orElse("");
            if (StringUtils.isEmpty(appId)) {
                throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg() + " appId is empty");
            }
            if (StringUtils.isEmpty(accessToken_expire)) {
                throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg() + " accessToken_expire is empty");
            }
            if (StringUtils.isEmpty(accessTokenCacheKey)) {
                throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg() + " accessTokenCacheKey is empty");
            }
            redisService.set(accessTokenCacheKey, JSON.toJSONString(getAccTokenVo), Parse.toLong(accessToken_expire));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error("process error e = ", e);
            throw new BusinessException(ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.code(), ErrorCodesEnum.RABBIT_MQ_BUSSINES_ERR.msg());
        }
    }
}
