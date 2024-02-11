package cc.backend.user.mapper;

import cc.backend.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/14
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comments> {
    List<Comments> getAllCommentByCreatedAt(@Param("postId") Integer postId);
    List<Comments> getAllCommentByTop(@Param("postId") Integer postId); // 热度值排列
}