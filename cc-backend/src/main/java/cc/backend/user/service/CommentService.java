package cc.backend.user.service;

import cc.backend.entity.Comments;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/14
 */
public interface CommentService {
    int getCommentCount(int postId);
    boolean insertComment(Comments comments,String tokenInfo);
    List<Comments> getAllCommentByCreatedAt(Integer postId);
    List<Comments> getAllCommentByTop(Integer postId);
    boolean submitAReply(Comments comments,String tokenInfo);
    boolean deletePersonalComment(Integer commentId,String tokenInfo);
}
