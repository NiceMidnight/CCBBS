package cc.backend.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
/**
 * @FileName RedisConfig
 * @Description Redis配置
 * @Author Tiamo_Null
 * @date 2023-08-03
 **/
@Configuration
public class RedisConfig {
    @Autowired
    private RedisKeyExpiredListener redisKeyExpiredListener;
    /**
     * @description TODO 设置redis的key/value序列化方式
     * @param factory
     * @return: org.springframework.data.redis.core.RedisTemplate<java.lang.String,java.lang.Object>
     */
    @Bean
    RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        //  关联
        template.setConnectionFactory(factory);
        //  设置key序列化方式
        template.setKeySerializer(RedisSerializer.string());
        //  设置value序列化方式
        template.setValueSerializer(RedisSerializer.string());
        return template;
    }

    /**
     * @description TODO redis键过期
     * @param connectionFactory
     * @return: org.springframework.data.redis.listener.RedisMessageListenerContainer
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisKeyExpiredListener, new PatternTopic("__keyevent@*__:expired"));
        return container;
    }
}
