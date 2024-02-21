package cc.backend.entity;

import cc.backend.enums.TokenStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/20
 */
@TableName("token")
@Data
public class Token {
    /**
     * 令牌id
     */
    @TableId(value = "token_id",type = IdType.AUTO)
    private Integer tokenId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * redis的键
     */
    private String redisKey;
    /**
     * redis的值
     */
    private String redisValue;
    /**
     * 令牌状态
     */
    private TokenStatus tokenStatus;

}
