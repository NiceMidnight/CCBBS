package cc.backend.user.mapper;

import cc.backend.entity.feedback.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/23
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {
    @Select("SELECT fb.*,tff.topic_name as topic_name,tff.topic_color as topic_color" +
            " FROM feedback fb " +
            "LEFT JOIN topic_for_feedback tff ON fb.topic_id = tff.topic_id " +
            "WHERE fb.creator_id = #{userId}")
    List<Feedback> selectAllByUserId(@Param("userId")Integer userId);
}
