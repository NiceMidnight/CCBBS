package cc.backend.manager.mapper;

import cc.backend.entity.feedback.TopicForFeedback;
import cc.backend.enums.TopicStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
public interface TopicForFeedbacksMapper extends BaseMapper<TopicForFeedback> {
    List<TopicForFeedback> selectTFF(@Param("topicStatus")TopicStatus topicStatus);

    @Update("UPDATE topic_for_feedback SET topic_status = #{topicStatus} WHERE topic_id = #{topicId}")
    int updateTopicStatus(@Param("topicId") Integer topicId,@Param("topicStatus") TopicStatus topicStatus);
    @Update("UPDATE topic_for_feedback SET topic_color = #{topicColor} WHERE topic_id = #{topicId}")
    int updateTopicColor(@Param("topicId") Integer topicId,@Param("topicColor") String topicColor);
}
