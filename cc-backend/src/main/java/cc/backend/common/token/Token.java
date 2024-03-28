package cc.backend.common.token;


import cc.backend.entity.User;
import cc.backend.enums.TokenStatus;
import cc.backend.manager.mapper.UsersMapper;
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
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private UsersMapper usersMapper;
    /**
     * @description TODO 生成token并以token为键user为值存入redis
     * @param user
     * @return: java.lang.String
     */
    @SneakyThrows
    public String TokenSave(User user)
    {
        // 使用 Jackson 序列化器将 User 对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String redisValue = objectMapper.writeValueAsString(user);
        String redisKey = insertTokenToRedis(redisValue);
        if (user.getUserVersion() != 0) //未登录过设置版本0表示登录
        {
            boolean isInsertToSQL = insertTokenToSQL(user, redisKey, redisValue);
            if (isInsertToSQL)
            {
                usersMapper.updateUserVersion(user.getId(),user.getUserName(),0);
                return redisKey;
            }
        }
        else {  //查找redis中是否存在有效的token值，有则清除重新生成token值给新登录用户
            String selectRedisKey = tokenMapper.selectRedisKey(user, TokenStatus.EFFECTIVE);
            boolean isDeletedFromRedis = TopDeletesTheToken(user,selectRedisKey);
            if (isDeletedFromRedis)
            {
                boolean isInsertToSQL = insertTokenToSQL(user, redisKey, redisValue);
                if (isInsertToSQL)
                {
                    return redisKey;
                }
            }
        }
        return null;
    }

    /**
     * @description TODO 插入redis
     * @param redisValue
     * @return: java.lang.String
     */
    private String insertTokenToRedis(String redisValue) {
        String loginToken = UUID.randomUUID() + "";
        redisTemplate.opsForValue().set(loginToken, redisValue, Duration.ofDays(7));
        return loginToken;
    }

    /**
     * @description TODO 插入数据库
     * @param user
     * @param redisKey
     * @param redisValue
     * @return: boolean
     */
    private boolean insertTokenToSQL(User user,String redisKey,String redisValue)
    {
        cc.backend.entity.Token token = new cc.backend.entity.Token();
        token.setUserId(user.getId());
        token.setUserName(user.getUserName());
        token.setRedisKey(redisKey);
        token.setRedisValue(redisValue);
        return tokenMapper.insert(token) > 0;
    }
    /**
     * @description TODO 获取登录信息获取用户id
     * @param tokenInfo
     * @return: int
     */
    @SneakyThrows
    public int getUserId(String tokenInfo) {
        String token = tokenInfo.substring(7);
        // 从 Redis 中获取字符串
        String retrievedUserString = (String) redisTemplate.opsForValue().get(token);
        // 使用 Jackson 反序列化为 User 对象
        User retrievedUser = objectMapper.readValue(retrievedUserString, User.class);
        if (retrievedUser != null) {
            return retrievedUser.getId();
        } else {
            return -1;
        }
    }

    /**
     * @description TODO 顶号清理token
     * @param token
     * @return: boolean
     */
    public boolean TopDeletesTheToken(User user,String token) {
        int isUpdate = tokenMapper.updateTokenStatus(user, TokenStatus.INVALID);
        if (isUpdate > 0)
        {
            Boolean isDeleted = redisTemplate.delete(token);
            return Boolean.TRUE.equals(isDeleted);
        }
        return false;
    }

    /**
     * @description TODO 退出登录清理token
     * @param tokenInfo
     * @return: boolean
     */
    public boolean deleteToken(String tokenInfo) {
        String token = tokenInfo.substring(7);
        int userId = getUserId(tokenInfo);
        tokenMapper.updateTokenStatusByRedisKey(token,TokenStatus.INVALID);
        usersMapper.updateUserVersionById(userId,1);
        Boolean isDeleted = redisTemplate.delete(token);
        return Boolean.TRUE.equals(isDeleted);
    }

    /**
     * @description TODO 查看redis是否存在键
     * @param key
     * @return: boolean
     */
    public boolean isKeyExists(String key)
    {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * @description TODO 获取用户对象
     * @param key
     * @return: int
     */
    @SneakyThrows
    public User getUser(String key) {
        // 从 Redis 中获取字符串
        String retrievedUserString = (String) redisTemplate.opsForValue().get(key);
        // 使用 Jackson 反序列化为 User 对象
        return objectMapper.readValue(retrievedUserString, User.class);
    }
}
