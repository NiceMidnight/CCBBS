package cc.backend.manager.mapper;


import cc.backend.entity.User;
import cc.backend.enums.UserStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * @FileName UsersMapper
 * @Description Users管理
 * @Author Tiamo_Null
 * @date 2023-08-04
 **/
@Mapper
public interface UsersMapper extends BaseMapper<User> {
    IPage<User> selectUser(IPage<User> iPage, User user,
                              @Param("startTime") LocalDateTime startTime,
                              @Param("endTime")LocalDateTime endTime);

    int updateUserVersion(@Param("id")Integer id,@Param("userName")String userName,@Param("version")Integer version);
    int updateUserVersionById(@Param("id")Integer id,@Param("version")Integer version);
    int updateUserStatus(@Param("user_id") int id, @Param("user_status")UserStatus userStatus);

    @Select("SELECT user_name FROM user WHERE id = #{id}")
    String findUserNameById(@Param("id") int id);

    User selectUserDateById(@Param("Id") Integer id);

    int updateUserRoleById(@Param("userId") int userId,
                           @Param("roleNumber") int roleNumber);

}
