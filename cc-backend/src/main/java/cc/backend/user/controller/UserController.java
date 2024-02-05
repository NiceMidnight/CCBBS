package cc.backend.user.controller;


import cc.backend.common.Result;
import cc.backend.entity.User;
import cc.backend.user.mapper.UserMapper;
import cc.backend.user.service.impl.UserServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * @FileName UserController
 * @Description User控制类
 * @Author Tiamo_Null
 * @date 2023-07-28
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DefaultKaptcha kaptcha;
    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;

    /**
     * @description TODO 登录验证
     * @param user
     * @param code
     * @return: cc.backend.common.Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, @RequestParam String code) {
        String token = null;
        if (redisTemplate.opsForValue().get(code) != null) {
            token = userService.login(user);
        } else return Result.error("验证码错误");
        if (token != null) { //  登录成功
            Object o = redisTemplate.opsForValue().get(token);
            System.out.println(o);
            return Result.successCTM(token,"登录成功...");
        }
        return Result.error("登录失败，用户名或密码错误...");
    }
    /**
     * @description TODO 验证码
     * @param request
     * @param response
     * @return: void
     */
    @GetMapping("/verify")
    public void verify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/jpeg");
        String code = kaptcha.createText();
        session.setAttribute("verify_code",code);
        //  redis存储code
        redisTemplate.opsForValue().set(code,code,60, TimeUnit.SECONDS);
        BufferedImage bi = kaptcha.createImage(code);
        try (ServletOutputStream outputStream = response.getOutputStream()){
            ImageIO.write(bi,"jpg",outputStream);
        }
        System.out.println("redis---"+redisTemplate.opsForValue().get(code));
        System.out.println("session---"+session.getAttribute("verify_code"));
    }

    /**
     * @description TODO 通过token获取个人用户信息
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader("Authorization") String tokenInfo) {
        User userInfo = userService.getUserInfo(tokenInfo);
        return Result.successCDT(userInfo,tokenInfo);
    }
    /**
     * @description TODO 通过userId获取用户信息
     * @param userId
     * @return: cc.backend.common.Result
     */
    @GetMapping("getOtherUserInfoById")
    public Result getOtherUserInfoById(@RequestParam("userId") Integer userId) {
        User otherUserInfoById = userService.getOtherUserInfoById(userId);
        return Result.successCDM(otherUserInfoById,"获取"+userId+"基础信息成功");
    }

    /**
     * @description TODO 修改个人信息
     * @param user
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody User user) {
        System.out.println(user);
        userService.updateUserInfo(user);
        return Result.successCM("修改成功");
    }

    /**
     * @description TODO 退出登录
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String tokenInfo) {

        return Result.successCM("登出...");
    }

    /**
     * @description TODO 获取用户头像
     * @param userId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getUserHead")
    public Result getUserHead(@RequestParam("userId") int userId) {
        String userHead = userService.getUserHead(userId);
        return Result.successCDM(userHead,"获取头像成功");
    }
}
