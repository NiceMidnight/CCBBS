package cc.backend.user.mapper;

import cc.backend.entity.Follow;

import cc.backend.enums.FollowStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/22
 */
@Mapper
public interface FollowMapper extends BaseMapper<Follow> {
    int insertFollow(Follow follow);
    int updateFollowStatus(@Param("followerId")Integer followerId,
                           @Param("followingId")Integer followingId,
                           @Param("followStatus")FollowStatus followStatus,
                           @Param("followDate")Date followDate);
}
