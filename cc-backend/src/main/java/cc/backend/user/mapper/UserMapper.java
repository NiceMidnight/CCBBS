package cc.backend.user.mapper;


import cc.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @FileName UserMapper
 * @Description
 * @Author Tiamo_Null
 * @date 2023-07-28
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int updateUser(@RequestBody User user);

}
