package cc.backend.config.redis;

import cc.backend.common.token.TokenMapper;
import cc.backend.entity.Token;
import cc.backend.enums.TokenStatus;
import cc.backend.manager.mapper.UsersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/21
 */
@Component
public class RedisKeyExpiredListener implements MessageListener {
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());
        System.out.println("Key expired: " + expiredKey);
        // 在这里添加处理过期键的逻辑
        if (expiredKey.length() != 4)
        {
            QueryWrapper<Token> tokenQueryWrapper = new QueryWrapper<>();
            tokenQueryWrapper.eq("redis_key",expiredKey);
            Token token = tokenMapper.selectOne(tokenQueryWrapper);
            if (token != null)
            {
                deleteDataForExpiredKey(expiredKey,token);
            }
        }
    }
    /**
     * @description TODO 更新数据库对应token的状态为无效
     * @param expiredKey
     * @param token
     * @return: void
     */
    @Synchronized
    private void deleteDataForExpiredKey(String expiredKey,Token token)
    {
        tokenMapper.updateTokenStatusByRedisKey(expiredKey, TokenStatus.INVALID);
        usersMapper.updateUserVersionById(token.getUserId(),1);
    }
}
