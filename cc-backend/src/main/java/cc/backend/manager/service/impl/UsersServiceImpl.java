package cc.backend.manager.service.impl;

import cc.backend.enums.UserStatus;
import cc.backend.entity.SearchData;
import cc.backend.entity.User;
import cc.backend.manager.mapper.UsersMapper;
import cc.backend.manager.service.UsersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @FileName UsersServiceImpl
 * @Description 用户管理
 * @Author Tiamo_Null
 * @date 2023-08-04
 **/
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    /**
     * @description TODO 根据条件查询所有用户
     * @param userSearchData
     * @return: cc.backend.entity.SearchData
     */
    @Override
    public SearchData<User> getAllUsers(SearchData<User> userSearchData) {
        //  分页查询
        IPage<User> iPage = new Page<>(userSearchData.getPageNum(), userSearchData.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like(userSearchData.getData().getUserName()!=null,"user_name",userSearchData.getData().getUserName())
                .like(userSearchData.getData().getUserSex()!=null,"user_sex",userSearchData.getData().getUserSex())
                .like("user_role",0);
        usersMapper.selectPage(iPage, queryWrapper);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(), iPage.getRecords());
    }
    /**
     * TODO 启用用户
     */
    @Override
    public boolean enableUser(Integer id) {
        return usersMapper.updateUserStatus(id,UserStatus.ENABLE) > 0 ;
    }
    /**
     * TODO 禁用用户
     */
    @Override
    public boolean forbidUser(Integer id) {
        return usersMapper.updateUserStatus(id,UserStatus.DISABLE) > 0;
    }

    /**
     * TODO 获取注册用户数量
     */
    @Override
    public Long getUserNumber() {
        return usersMapper.selectCount(null);
    }

    /**
     * @description TODO 获取用户信息
     * @param id
     * @return: cc.backend.entity.User
     */
    @Override
    public User getUserInfo(Integer id) {
        return usersMapper.selectUserDateById(id);
    }


    @Override
    public boolean updateUserRole(int userId, int roleNumber) {
        return usersMapper.updateUserRoleById(userId,roleNumber) > 0;
    }


}
