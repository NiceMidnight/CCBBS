package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.manager.service.impl.TokensServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 令牌控制器
 * @Author Tiamo_null
 * @Date 2024/2/20
 */
@RestController
@RequestMapping("/manager/token")
public class TokensController {
    @Autowired
    private TokensServiceImpl tokensService;
    @GetMapping("/tokenVerification")
    public Result tokenVerification(String tokenInfo)
    {
        boolean isLegitimate = tokensService.tokenVerification(tokenInfo);
        if (isLegitimate)
        {
            return Result.successCM("管理员合法登录");
        }
        return Result.error("管理员非法登录，已登出");
    }
}
