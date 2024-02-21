package cc.backend.common.token;

import cc.backend.entity.Token;
import cc.backend.entity.User;
import cc.backend.enums.TokenStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/20
 */
@Mapper
public interface TokenMapper extends BaseMapper<Token> {
    String selectRedisKey(User user, @Param("tokenStatus")TokenStatus tokenStatus);
    int updateTokenStatus(User user, @Param("tokenStatus")TokenStatus tokenStatus);
    int updateTokenStatusByRedisKey(@Param("redisKey")String redisKey,@Param("tokenStatus")TokenStatus tokenStatus);

}
