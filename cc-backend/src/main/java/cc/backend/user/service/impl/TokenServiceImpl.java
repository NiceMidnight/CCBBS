package cc.backend.user.service.impl;

import cc.backend.common.token.Token;
import cc.backend.common.token.TokenMapper;
import cc.backend.entity.User;
import cc.backend.enums.TokenStatus;
import cc.backend.user.service.TokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/20
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private Token token;
    @Autowired
    private TokenMapper tokenMapper;
    /**
     * TODO token校验用户是否合法
     */
    @Override
    public boolean tokenVerification(String tokenInfo) {
        boolean keyExists = token.isKeyExists(tokenInfo);
        if (keyExists)
        {
            User user = token.getUser(tokenInfo);
            if (user != null)
            {
                return user.getUserRole() == 0 || user.getUserRole() == 2 ;
            }
        }
        return false;
    }

    /**
     * TODO 获取登录数
     */
    @Override
    public Long getTheNumOfLogins() {
        QueryWrapper<cc.backend.entity.Token> tokenQueryWrapper = new QueryWrapper<>();
        tokenQueryWrapper.eq("token_status", TokenStatus.EFFECTIVE);
        return tokenMapper.selectCount(tokenQueryWrapper);
    }
}
