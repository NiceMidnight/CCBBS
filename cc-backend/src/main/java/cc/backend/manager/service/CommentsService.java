package cc.backend.manager.service;

import cc.backend.entity.Comments;
import cc.backend.entity.SearchData;
import cc.backend.enums.CommentStatusForCompliance;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/17
 */
public interface CommentsService {
    IPage<Comments> getOnePostComments(SearchData<Comments> commentsSearchData, LocalDateTime startTime, LocalDateTime endTime);
    IPage<Comments> getAllPostComments(SearchData<Comments> commentsSearchData, LocalDateTime startTime, LocalDateTime endTime);
    boolean updateCommentStatusForCompliance(Integer commentId,CommentStatusForCompliance statusForCompliance);
    Comments getOneComment(Integer commentId);
}
