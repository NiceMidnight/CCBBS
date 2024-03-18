package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.user.service.impl.TokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 进入页面token校验
 * @Author Tiamo_null
 * @Date 2024/2/20
 */
@RestController
@RequestMapping("/user/token")
public class TokenController {
    @Autowired
    private TokenServiceImpl tokenService;
    /**
     * @description TODO token校验用户是否合法
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/tokenVerification")
    public Result tokenVerification(@RequestParam("token") String tokenInfo)
    {
        boolean isLegitimate = tokenService.tokenVerification(tokenInfo);
        if (isLegitimate)
        {
            return Result.successCM("用户合法登录");
        }
        return Result.error("用户非法登录，已登出");
    }

}
