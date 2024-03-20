package cc.backend.manager.mapper;

import cc.backend.entity.feedback.Feedback;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.FeedbackStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/15
 */
public interface FeedbacksMapper extends BaseMapper<Feedback> {

    IPage<Feedback> selectAllFeedbackMessage(IPage<Feedback> iPage, Feedback feedback,
                                   @Param("startTime") LocalDateTime startTime,
                                   @Param("endTime")LocalDateTime endTime);

    int updateFeedbackStatus(@Param("id")Integer id, @Param("FeedbackStatus")FeedbackStatus feedbackStatus);

    @Select("SELECT COUNT(*) FROM feedback WHERE created_time >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) " +
            "AND created_time < DATE_ADD(CURDATE(), INTERVAL 1 DAY)")
    Long countByCurrentWeek();

    @Select("SELECT COUNT(*) FROM feedback WHERE created_time >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) + 7 DAY) " +
            "AND created_time < DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)")
    Long countByLastWeek();

}
