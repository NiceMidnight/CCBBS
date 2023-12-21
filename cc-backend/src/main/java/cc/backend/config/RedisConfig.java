package cc.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @FileName RedisConfig
 * @Description Redis配置
 * @Author Tiamo_Null
 * @date 2023-08-03
 **/
@Configuration
public class RedisConfig {
    //  设置redis的key/value序列化方式
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
}
