package cc.backend.manager.mapper;

import cc.backend.entity.feedback.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/15
 */
public interface FeedbacksMapper extends BaseMapper<Feedback> {
    @Select("SELECT COUNT(*) FROM feedback WHERE created_time >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) " +
            "AND created_time < DATE_ADD(CURDATE(), INTERVAL 1 DAY)")
    Long countByCurrentWeek();

    @Select("SELECT COUNT(*) FROM feedback WHERE created_time >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) + 7 DAY) " +
            "AND created_time < DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)")
    Long countByLastWeek();
}
