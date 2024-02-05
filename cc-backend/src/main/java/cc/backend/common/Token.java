package cc.backend.common;


import cc.backend.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public String TokenSave(User user) throws JsonProcessingException {
        // 生成token令牌
        String loginToken = UUID.randomUUID() + "";
        // 使用 Jackson 序列化器将 User 对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedUser = objectMapper.writeValueAsString(user);
        // 存储到 Redis
        redisTemplate.opsForValue().set(loginToken, serializedUser, Duration.ofDays(7));
        return loginToken;
    }

    /**
     * @description TODO 获取登录信息
     * @date 2023/8/14 17:37
     */

    @SneakyThrows
    public int getUserId(String tokenInfo) {
        String token = tokenInfo.substring(7);
        // 从 Redis 中获取字符串
        String retrievedUserString = (String) redisTemplate.opsForValue().get(token);
        // 使用 Jackson 反序列化为 User 对象
        User retrievedUser = objectMapper.readValue(retrievedUserString, User.class);
        // 现在你可以使用 'retrievedUser' 对象了
        if (retrievedUser != null) {
            System.out.println("检索的用户ID: " + retrievedUser.getId());
            return retrievedUser.getId();
        } else {
            return -1;
        }
    }
    /**
     * @description TODO redis删除token
     * @param tokenInfo
     * @return: boolean
     */
    public boolean deleteToken(String tokenInfo) {
        String token = tokenInfo.substring(7);
        Boolean isDeleted = redisTemplate.delete(token);
        if (Boolean.TRUE.equals(isDeleted)) return true;
        return false;
    }
}
