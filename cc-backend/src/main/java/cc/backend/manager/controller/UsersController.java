package cc.backend.manager.controller;


import cc.backend.common.Result;
import cc.backend.entity.SearchData;
import cc.backend.entity.User;
import cc.backend.manager.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @description TODO 根据条件获取所有用户信息
     * @param userSearchData
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/getAllUsers")
    public Result getAllUsers(@RequestBody SearchData<User> userSearchData){
        SearchData allUsers = usersService.getAllUsers(userSearchData);
        return Result.successCDM(allUsers,"获取所有用户信息");
    }
    /**
     * @description TODO 启用用户
     * @param id
     * @return: cc.backend.common.Result
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
     * @description TODO 禁止用户
     * @param id
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/forbidUser")
    public Result forbidUser(Integer id) {
        boolean forbidUser = usersService.forbidUser(id);
        if (forbidUser) {
            return Result.successCDM(id,"禁用操作成功");
        }
        return Result.error("禁用操作失败");
    }

    /**
     * @description TODO 获取用户数量
     *
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/getUserNumber")
    public Result getUserNumber() {
        Long userNumber = usersService.getUserNumber();
        return Result.successCDM(userNumber,"获取用户数成功");
    }

    /**
     * @description TODO 通过id获取用户信息
     * @param id
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam("id")Integer id)
    {
        System.out.println(id);
        User userInfo = usersService.getUserInfo(id);
        if (userInfo != null)
        {
            return Result.successCDM(userInfo,"获取用户信息成功");
        }
        return Result.error("获取用户信息失败");
    }

}
