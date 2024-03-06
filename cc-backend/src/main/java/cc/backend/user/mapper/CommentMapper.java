package cc.backend.user.mapper;

import cc.backend.entity.Comments;
import cc.backend.enums.CommentStatusForUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    Page<Comments> selectReplyComments(IPage commentSearchData, @Param("userId")Integer userId);
    int selectReplyCommentsCount(@Param("userId")Integer userId);
    int updateStatusForUser(@Param("commentId")Integer commentId,
                            @Param("statusForUser")CommentStatusForUser commentStatusForUser);
}