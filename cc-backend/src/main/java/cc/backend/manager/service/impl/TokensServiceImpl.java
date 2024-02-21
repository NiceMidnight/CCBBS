package cc.backend.manager.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.User;
import cc.backend.manager.mapper.TokensMapper;
import cc.backend.manager.service.TokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/20
 */
@Service
public class TokensServiceImpl implements TokensService {
    @Autowired
    private TokensMapper tokensMapper;
    @Autowired
    private Token token;

    /**
     * @description TODO token校验用户是否合法
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean tokenVerification(String tokenInfo) {
        boolean keyExists = token.isKeyExists(tokenInfo);
        if (keyExists)
        {
            User user = token.getUser(tokenInfo);
            if (user != null)
            {
                return user.getUserRole() == 1;
            }
        }
        return false;
    }
}
