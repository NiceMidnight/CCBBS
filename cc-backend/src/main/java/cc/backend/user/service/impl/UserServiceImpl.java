package cc.backend.user.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.User;
import cc.backend.enums.UserStatus;
import cc.backend.user.mapper.UserMapper;
import cc.backend.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;


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
     * @description TODO 登录验证
     * @param user
     * @return: java.lang.String
     */
    @SneakyThrows
    @Override
    public String login(User user) {
        //  用户名密码验证
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(true,User::getUserName,user.getUserName())
                .eq(true,User::getPassword,user.getPassword())
                .eq(User::getUserStatus, UserStatus.ENABLE) //用户状态是否合法
                .eq(User::getUserDeleted, 1)    //  逻辑删除,判断用户是否存在
                .ne(User::getUserRole,1);       //  用户角色--学生
        User isExist = userMapper.selectOne(lambdaQueryWrapper);
        if (isExist != null) { //  登录成功
            return token.TokenSave(isExist);
        }
        return null;
    }

    /**
     * @description TODO 获取用户信息
     * @param loginToken
     * @return: cc.backend.entity.User
     */
    @Override
    public User getUserInfo(String loginToken) {
        int userId = token.getUserId(loginToken);
        return userMapper.selectById(userId);
    }

    @Override
    public User getOtherUserInfoById(Integer userId) {
        return userMapper.selectById(userId);
    }

    /**
     * @description TODO 更新用户信息
     * @param user
     * @return: boolean
     */
    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateUser(user) > 0;
    }

    /**
     * @description TODO 获取用户头像
     * @param userId
     * @return: java.lang.String
     */
    @Override
    public String getUserHead(int userId) {
        String userHead = userMapper.getUserHeadById(userId);
        return userHead;
    }

    /**
     * @description TODO 更新用户粉丝数
     * @param userId
     * @param addOrDel
     * @return: boolean
     */
    @Override
    public boolean updateUserFans(Integer userId, boolean addOrDel) {
        User user = userMapper.selectById(userId);
        if (user != null)
        {
            if (addOrDel)
            {
                user.setFans(user.getFans() + 1);
                UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
                userUpdateWrapper.eq("id",userId);
                return userMapper.update(user,userUpdateWrapper) > 0;
            }
            else
            {
            user.setFans(user.getFans() - 1);
            UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
            userUpdateWrapper.eq("id",userId);
            return userMapper.update(user,userUpdateWrapper) > 0;
            }
        }
        return false;
    }

    /**
     * @description TODO 注册
     * @param user
     * @return: boolean
     */
    @Override
    public boolean register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName());
        User users = userMapper.selectOne(queryWrapper);
        if (users != null)
        {
            return false;
        }
        user.setUserStatus(UserStatus.DISABLE);
        user.setUserRole(2);
        user.setUserDate(new Date());
        return userMapper.insert(user) > 0;
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

    /**
     * TODO 获取用户角色
     */
    @Override
    public Integer getUserRole(String tokenInfo) {
        String key = tokenInfo.substring(7);
        User user = token.getUser(key);
        return user.getUserRole();
    }

}
