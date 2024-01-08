package cc.backend.manager.mapper;

import cc.backend.entity.forpost.TopicForPost;
import cc.backend.enums.TopicStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
@Mapper
public interface TopicForPostsMapper extends BaseMapper<TopicForPost> {
    IPage<TopicForPost> selectTFP(IPage<TopicForPost> iPage,@Param("topicName") String topicName);
    int updateTFPStatus(@Param("topicId") Integer topicId,
                        @Param("topicStatus") TopicStatus topicStatus,
                        @Param("updatedId")Long updatedId,
                        @Param("updatedTime")Date updatedTime);
}
