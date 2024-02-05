package cc.backend.user.service;


import cc.backend.entity.User;

/**
 * @FileName UserService
 * @Description
 * @Author Tiamo_Null
 * @date 2023-07-29
 **/
public interface UserService {
    String login(User user);
    User getUserInfo(String token);
    User getOtherUserInfoById(Integer userId);
    boolean updateUserInfo(User user);
    String getUserHead(int userId);
}
