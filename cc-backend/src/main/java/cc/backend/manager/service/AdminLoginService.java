package cc.backend.manager.service;


import cc.backend.entity.User;

/**
 * @FileName AdminLoginService
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-14
 **/
public interface AdminLoginService {
    String login(User user);
    User getManagerInfo(String token);
    String getUserName(String token);
    boolean logout(String tokenInfo);
}
