package cc.backend.user.mapper;

import cc.backend.entity.DailyUniqueVisitors;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
public interface LogMapper extends BaseMapper<DailyUniqueVisitors> {
    @Select("SELECT COUNT(*) FROM daily_unique_visitors WHERE user_id = #{userId} AND DATE(first_access_time) = CURDATE()")
    int countByUserIdAndDate(@Param("userId") Integer userId);
}
