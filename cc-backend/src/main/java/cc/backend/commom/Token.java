package cc.backend.commom;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * @FileName TokenSave
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-14
 **/
@Service
public class Token {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RedisTemplate<String ,Object> redisTemplate;

    /**
     * @description TODO 生成token并以token为键user为值存入redis
     * @date 2023/8/14 17:25
     */
    public String TokenSave(int id) {
        // 生成token令牌
        String loginToken = UUID.randomUUID() + "";
        //  存redis数据库
        redisTemplate.opsForValue().set(loginToken, String.valueOf(id), Duration.ofDays(7));
        return loginToken;
    }

    /**
     * @description TODO 获取登录信息
     * @date 2023/8/14 17:37
     */
    @SneakyThrows
    public int TokenInfo(String loginToken) {
        return Integer.valueOf((String) redisTemplate.opsForValue().get(loginToken));
    }

    @SneakyThrows
    public int getUserId(String tokenInfo) {
        String token = tokenInfo.substring(7);
        return Integer.valueOf((String) redisTemplate.opsForValue().get(token));
    }
}
