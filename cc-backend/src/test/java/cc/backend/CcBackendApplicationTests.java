package cc.backend;

import cc.backend.commom.Token;
import cc.backend.entity.Dict;
import cc.backend.manager.mapper.DictMapper;
import cc.backend.manager.service.impl.ArticlesServiceImpl;
import cc.backend.manager.service.impl.DictServiceImpl;
import cc.backend.user.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

@SpringBootTest
class CcBackendApplicationTests {
    @Autowired
    private DictServiceImpl dictService;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private ArticlesServiceImpl articleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Token token;
    @Autowired
    RedisTemplate<String ,Object> redisTemplate;
    @Test
    void contextLoads() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.select("dict_type_name, MAX(dict_id) as max_dict_id, MAX(dict_type_id) as max_dict_type_id, MAX(dict_item_name) as max_dict_item_name");
        dictQueryWrapper.groupBy("dict_type_name");
        System.out.println(dictMapper.selectList(dictQueryWrapper));
    }
//    @Test
//    void passwordEncipher() {
//        // 创建BCryptPasswordEncoder
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        // 模拟用户注册过程
//        String rawPassword = "root";
//
//        // 加密密码
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        System.out.println("密码已加密并保存: " + encodedPassword);
//
//        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
//        // 模拟用户登录过程
//        String loginAttempt = "root";
//
//        // 验证密码
//        if (passwordEncoder2.matches(loginAttempt, encodedPassword)) {
//            System.out.println("密码验证成功，登录成功！");
//        } else {
//            System.out.println("密码验证失败，登录失败！");
//        }
//    }



}
