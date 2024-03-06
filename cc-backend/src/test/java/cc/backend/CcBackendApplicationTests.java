package cc.backend;

import cc.backend.common.token.Token;
import cc.backend.entity.Comments;
import cc.backend.entity.User;
import cc.backend.enums.CommentStatusForCompliance;
import cc.backend.manager.mapper.DictMapper;
import cc.backend.manager.service.impl.ArticlesServiceImpl;
import cc.backend.manager.service.impl.DictServiceImpl;
import cc.backend.user.mapper.CommentMapper;
import cc.backend.user.mapper.UserMapper;
import cc.backend.user.service.impl.CommentServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

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
    void contextLoads() throws JsonProcessingException {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.select(User::getUserName, User::getId).eq(User::getId, 1);
        User user = userMapper.selectOne(userLambdaQueryWrapper);

        // 使用 Jackson 序列化器将 User 对象转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String serializedUser = objectMapper.writeValueAsString(user);

        // 存储到 Redis
        redisTemplate.opsForValue().set("user", serializedUser, Duration.ofDays(1));

        // 从 Redis 中获取字符串
        String retrievedUserString = (String) redisTemplate.opsForValue().get("user");

        // 使用 Jackson 反序列化为 User 对象
        User retrievedUser = objectMapper.readValue(retrievedUserString, User.class);

        // 现在你可以使用 'retrievedUser' 对象了
        if (retrievedUser != null) {
            System.out.println("检索的用户ID: " + retrievedUser.getId());
        } else {
            System.out.println("User not found in Redis.");
        }
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

    @Autowired
    private CommentServiceImpl commentService;
    @Test
    void getComment()
    {
        List<Comments> allComment = commentService.getAllCommentByCreatedAt(1);
        System.out.println(allComment);
    }

    @Autowired
    private CommentMapper commentMapper;
    @Test
    void getCommentStatus()
    {
//        IPage<Comments> iPage = new Page<>(1, 50);
//        int  i= commentMapper.selectReplyCommentsCount(2);
//        System.out.println("数量有："+i);
        System.out.println(Arrays.toString(CommentStatusForCompliance.values()));
    }
}
