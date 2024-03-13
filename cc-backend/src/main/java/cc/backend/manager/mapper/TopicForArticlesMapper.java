package cc.backend.manager.mapper;

import cc.backend.entity.TopicForArticle;
import cc.backend.enums.TopicStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
public interface TopicForArticlesMapper extends BaseMapper<TopicForArticle> {
    @Select("SELECT tfa.*, u.user_name AS created_by " +
            "FROM topic_for_article tfa " +
            "LEFT JOIN user u ON tfa.created_id = u.id")
    List<TopicForArticle> selectTopicForArticle();

    @Update("UPDATE topic_for_article " +
    "SET topic_status = #{topicStatus} " +
    "WHERE topic_id = #{topicId}")
    int updateTopicStatus(@Param("topicId") Integer topicId,
                          @Param("topicStatus")TopicStatus topicStatus);

    @Update("UPDATE topic_for_article " +
            "SET topic_color = #{topicColor} " +
            "WHERE topic_id = #{topicId}")
    int updateTopicColor(@Param("topicId") Integer topicId,
                          @Param("topicColor")String topicColor);
}
