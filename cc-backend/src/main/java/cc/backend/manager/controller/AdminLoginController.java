package cc.backend.manager.controller;


import cc.backend.common.Result;
import cc.backend.entity.User;
import cc.backend.manager.service.impl.AdminLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * @FileName AdminLoginController
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-04
 **/
@RestController
@RequestMapping("/manager")
public class AdminLoginController {
    @Autowired
    AdminLoginServiceImpl adminLoginService;

    /**
     * @description TODO 登录
     * @date 2023/8/14 17:20
     */
    @RequestMapping("/login")
    public Result login(@RequestBody User user) {
        String token = adminLoginService.login(user);
        if (token != null) { //  登录成功
            return Result.successCTM(token,"登录成功...");
        }
        return Result.error("登录失败，用户名或密码错误...");
    }

    /**
     * @description TODO 获取信息
     * @date 2023/8/14 17:21
     */
    @RequestMapping("/getInfo") //通过redis获取管理员信息
    public Result getInfo(@RequestHeader("Authorization") String tokenInfo) {
//        System.out.println(token);
        User managerInfo = adminLoginService.getManagerInfo(tokenInfo);
        return Result.successCDT(managerInfo.getUserName(),tokenInfo);
    }

    /**
     * TODO 获取用户名称
     */
    @GetMapping("/getUserName")
    public Result getUserName(@RequestHeader("Authorization") String tokenInfo) {
        String userName = adminLoginService.getUserName(tokenInfo);
        return Result.successCDM(userName,"获取用户名称成功");
    }

    @RequestMapping("/logout")
    public Result logout() {
        return Result.successCM("登出...");
    }

}
