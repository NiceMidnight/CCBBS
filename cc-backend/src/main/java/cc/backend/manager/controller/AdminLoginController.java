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
     * @param user
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/login")
    public Result login(@RequestBody User user) {
        String token = adminLoginService.login(user);
        System.out.println(token);
        if (token != null) { //  登录成功
            return Result.successCTM(token,"登录成功...");
        }
        return Result.error("登录失败...");
    }

    /**
     * @description TODO 获取管理员信息
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/getInfo") //通过redis获取管理员信息
    public Result getInfo(@RequestHeader("Authorization") String tokenInfo) {
        User managerInfo = adminLoginService.getManagerInfo(tokenInfo);
        return Result.successCDT(managerInfo.getUserName(),tokenInfo);
    }

    /**
     * @description TODO 获取管理员名称
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getUserName")
    public Result getUserName(@RequestHeader("Authorization") String tokenInfo) {
        String userName = adminLoginService.getUserName(tokenInfo);
        return Result.successCDM(userName,"获取用户名称成功");
    }

    /**
     * @description TODO 后台登出
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization")String tokenInfo) {
        boolean isLogout = adminLoginService.logout(tokenInfo);
        if (isLogout) {
            return Result.successCM("后台成功登出。。。");
        }
        return Result.error("后台登出失败。。。");
    }

}
