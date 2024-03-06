package cc.backend.manager.service;


import cc.backend.entity.SearchData;
import cc.backend.entity.User;


/**
 * @FileName UsersService
 * @Description 后台用户管理
 * @Author Tiamo_Null
 * @date 2023-08-04
 **/
public interface UsersService {
    SearchData<User> getAllUsers(SearchData<User> userSearchData);

    boolean enableUser(Integer id);
    boolean forbidUser(Integer id);
    Long getUserNumber();
    User getUserInfo(Integer id);

}
