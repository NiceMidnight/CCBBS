package cc.backend.manager.mapper;

import cc.backend.entity.Post;
import cc.backend.enums.PostStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/24
 */
@Mapper
public interface PostsMapper extends BaseMapper<Post> {
    IPage<Post> selectAllPost(IPage<Post> iPage, Post post,
                              @Param("startTime") LocalDateTime startTime,
                              @Param("endTime")LocalDateTime endTime);
    int updatePostStatus(@Param("post_id")int postId,@Param("post_status") PostStatus postStatus);

    String selectPostTitleById(@Param("postId")Integer postId);

    @Select("SELECT COUNT(*) FROM post WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) " +
            "AND created_at < DATE_ADD(CURDATE(), INTERVAL 1 DAY)")
    Long countByCurrentWeek();

    @Select("SELECT COUNT(*) FROM post WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) + 7 DAY) " +
            "AND created_at < DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)")
    Long countByLastWeek();
}
