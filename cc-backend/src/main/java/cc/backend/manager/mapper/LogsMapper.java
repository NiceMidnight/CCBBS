package cc.backend.manager.mapper;

import cc.backend.entity.DailyUniqueVisitors;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
public interface LogsMapper extends BaseMapper<DailyUniqueVisitors> {
    @Select("SELECT COUNT(*) FROM daily_unique_visitors WHERE DATE(first_access_time) = CURDATE()")
    Long countByToday();

    @Select("SELECT COUNT(*) FROM daily_unique_visitors WHERE DATE(first_access_time) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)")
    Long countByYesterday();

    @Select("SELECT COUNT(*) FROM daily_unique_visitors WHERE YEAR(first_access_time) = YEAR(CURDATE()) " +
            "AND MONTH(first_access_time) = MONTH(CURDATE())")
    Long countByCurrentMonth();

    @Select("SELECT COUNT(*) FROM daily_unique_visitors WHERE YEAR(first_access_time) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND MONTH(first_access_time) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)")
    Long countByLastMonth();
}
