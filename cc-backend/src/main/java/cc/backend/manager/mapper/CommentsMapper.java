package cc.backend.manager.mapper;

import cc.backend.entity.Comments;
import cc.backend.enums.CommentStatusForCompliance;
import cc.backend.enums.SysImgStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/17
 */
public interface CommentsMapper extends BaseMapper<Comments> {
    IPage<Comments> selectOnePostCommentsBySearchData(IPage<Comments> iPage,
                                          Comments comments,
                                          @Param("startTime")LocalDateTime startTime,
                                          @Param("endTime")LocalDateTime endTime);

    IPage<Comments> selectAllCommentsBySearchData(IPage<Comments> iPage,
                                                      Comments comments,
                                                      @Param("startTime")LocalDateTime startTime,
                                                      @Param("endTime")LocalDateTime endTime);

    int updateCommentStatusForComplianceById(@Param("commentId") int commentId,
                                             @Param("commentStatusForCompliance") CommentStatusForCompliance commentStatusForCompliance);
}
