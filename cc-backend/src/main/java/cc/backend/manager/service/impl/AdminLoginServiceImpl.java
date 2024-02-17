package cc.backend.manager.service.impl;

import cc.backend.common.Token;
import cc.backend.entity.User;
import cc.backend.manager.mapper.UsersMapper;
import cc.backend.manager.service.AdminLoginService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



/**
 * @FileName AdminLoginServiceImpl
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-14
 **/
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    UsersMapper usersMapper;
    @Autowired
    RedisTemplate<String ,Object> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Token token;
    /**
     * @description TODO 登录验证
     * @date 2023/8/14 17:25
     */
    @SneakyThrows
    @Override
    public String login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(true,User::getUserName,user.getUserName())
                .eq(true,User::getPassword,user.getPassword())
                .eq(User::getUserDeleted, 1)    //  逻辑删除
                .eq(User::getUserRole,1)    //  用户角色--管理员
                .select(User::getId,User::getUserName,User::getUserRole,User::getUserDeleted,User::getUserStatus,User::getNickName);
        User isExist = usersMapper.selectOne(lambdaQueryWrapper);
        if (isExist != null) {
            // 用户存在，并且密码匹配，登录成功
            return token.TokenSave(isExist);
        }
        return null;
    }

    /**
     * @description TODO 根据token获取管理员信息
     * @date 2023/8/14 17:26
     */
    @SneakyThrows
    @Override
    public User getManagerInfo(String tokenInfo) {
        int userId = this.token.getUserId(tokenInfo);
        User user = usersMapper.selectById(userId);
        return user;
    }

    /**
     * TODO 获取用户名称
     */
    @Override
    public String getUserName(String tokenInfo) {
        int userId = this.token.getUserId(tokenInfo);
        String userName = usersMapper.findUserNameById(userId);
        return userName;
    }
    /**
     * @description TODO 退出登录清除token
     * @param tokenInfo
     * @return: void
     */
    @Override
    public boolean logout(String tokenInfo) {
        return token.deleteToken(tokenInfo);
    }
}
