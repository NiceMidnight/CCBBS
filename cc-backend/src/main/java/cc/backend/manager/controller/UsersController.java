package cc.backend.manager.controller;


import cc.backend.common.Result;
import cc.backend.entity.SearchData;
import cc.backend.entity.User;
import cc.backend.manager.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName UsersController
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-04
 **/
@RestController
@RequestMapping("/manager/user")
public class UsersController {
    @Autowired
    private UsersServiceImpl usersService;

    /**
     * TODO 根据条件获取所有用户信息
     */
    @RequestMapping("/getAllUsers")
    public Result getAllUsers(@RequestBody SearchData<User> userSearchData){
        SearchData allUsers = usersService.getAllUsers(userSearchData);
        return Result.successCDM(allUsers,"获取所有用户信息");
    }
    /**
     * TODO 启用用户
     */
    @RequestMapping("/enableUser")
    public Result enableUser(Integer id) {
        boolean enableUser = usersService.enableUser(id);
        if (enableUser) {
            return Result.successCDM(id,"启用操作成功");
        }
        return Result.error("启用操作失败");
    }

    /**
     * TODO  禁止用户
     */
    @RequestMapping("/forbidUser")
    public Result forbidUser(Integer id) {
        boolean forbidUser = usersService.forbidUser(id);
        if (forbidUser) {
            return Result.successCDM(id,"禁用操作成功");
        }
        return Result.error("禁用操作失败");
    }
    @RequestMapping("/getUserNumber")
    public Result getUserNumber() {
        Long userNumber = usersService.getUserNumber();
        return Result.successCDM(userNumber,"获取用户数成功");
    }
}
