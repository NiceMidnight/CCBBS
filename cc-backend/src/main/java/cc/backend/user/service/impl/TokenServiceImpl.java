package cc.backend.user.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.User;
import cc.backend.user.service.TokenService;
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
                return user.getUserRole() == 0;
            }
        }
        return false;
    }
}
