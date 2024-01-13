package cc.backend.user.service.impl;

import cc.backend.commom.Token;
import cc.backend.entity.User;
import cc.backend.user.mapper.UserMapper;
import cc.backend.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @FileName UserServiceImpl
 * @Description
 * @Author Tiamo_Null
 * @date 2023-07-29
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private Token token;
    @Autowired
    RedisTemplate<String ,Object> redisTemplate;
    /**
     * TODO 登录验证
     */
    @SneakyThrows
    @Override
    public String login(User user) {
        //  用户名密码验证
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(true,User::getUserName,user.getUserName())
                .eq(true,User::getPassword,user.getPassword())
                .eq(User::getUserDeleted, 1)    //  逻辑删除,判断用户是否存在
                .eq(User::getUserRole,0);       //  用户角色--用户
        User isExist = userMapper.selectOne(lambdaQueryWrapper);
        if (isExist != null) { //  登录成功
            return token.TokenSave(isExist);
        }
        return null;
    }

    /**
     * TODO 获取用户信息
     */
    @Override
    public User getUserInfo(String loginToken) {
        int userId = token.getUserId(loginToken);
        User user = userMapper.selectById(userId);
        return user;
    }

    /**
     * TODO 更新用户信息
     */
    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateUser(user) > 0;
    }

    /**
     * TODO 获取用户头像
     */
    @Override
    public String getUserHead(int userId) {
        String userHead = userMapper.getUserHeadById(userId);
        System.out.println(userHead);
        return userHead;
    }
}
