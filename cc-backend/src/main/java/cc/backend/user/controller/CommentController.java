package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.Comments;
import cc.backend.user.service.impl.CommentServiceImpl;
import cc.backend.user.service.impl.PostServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/14
 */
@RestController
@RequestMapping("/user/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private PostServiceImpl postService;
    @GetMapping("/getCommentCount")
    public Result getCommentCount(@RequestParam("postId") Integer postId) {
        int commentCount = commentService.getCommentCount(postId);
        return Result.successCDM(commentCount,"获取评论数量成功");
    }

    /**
     * @description TODO 提交评论
     * @param comments
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @RequestMapping("/postAComment")
    public Result postAComment(@RequestBody Comments comments,@RequestHeader("Authorization")String tokenInfo)
    {
        boolean isInsert = commentService.insertComment(comments,tokenInfo);
        if (isInsert)
        {
            boolean isUpdate = postService.updatePostCommentCount(comments.getPostId(),true);
            if (isUpdate)
            {
                return Result.successCM("发布评论成功");
            }
            return Result.error("更新帖子评论数量失败");
        }
        return Result.error("插入评论失败");
    }

    /**
     * @description TODO 获取最热评论
     * @param postId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getTopComments")
    public Result getTopComments(@RequestParam("postId") Integer postId)
    {
        List<Comments> allCommentByTop = commentService.getAllCommentByTop(postId);
        if (allCommentByTop != null)
        {
            System.out.println(allCommentByTop);
            return Result.successCDM(allCommentByTop,"获取最热评论成功");
        }
        return Result.error("获取最热评论失败");
    }

    /**
     * @description TODO 获取最新评论
     * @param postId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getLatestComments")
    public Result getLatestComments(@RequestParam("postId") Integer postId)
    {
        List<Comments> allCommentByCreatedAt = commentService.getAllCommentByCreatedAt(postId);
        if (allCommentByCreatedAt != null)
        {
            System.out.println(allCommentByCreatedAt);
            return Result.successCDM(allCommentByCreatedAt,"获取最新评论成功");
        }
        return Result.error("获取最新评论失败");
    }

    /**
     * @description TODO 提交回复评论
     * @param comments
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @RequestMapping("/submitAReply")
    public Result submitAReply(@RequestBody Comments comments,@RequestHeader("Authorization")String tokenInfo)
    {
        System.out.println(comments);
        boolean isSubmit = commentService.submitAReply(comments,tokenInfo);
        if (isSubmit)
        {
            boolean isUpdate = postService.updatePostCommentCount(comments.getPostId(),true);
            if (isUpdate)
            {
                return Result.successCM("提交回复评论成功");
            }
            return Result.error("更新帖子评论数量失败");
        } return Result.error("评论回复提交失败");
    }

    /**
     * @description TODO 删除个人评论
     * @param commentId
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @GetMapping("/deletePersonalComment")
    public Result deletePersonalComment(
            @RequestParam("commentId") Integer commentId,
            @RequestParam("postId") Integer postId,
            @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isDeletePersonalComment = commentService.deletePersonalComment(commentId,tokenInfo);
        if (isDeletePersonalComment)
        {
            boolean isUpdate = postService.updatePostCommentCount(postId,false);
            if (isUpdate)
            {
                return Result.successCM("删除个人评论成功");
            }
            return Result.error("更新帖子评论数量失败");
        }
        return Result.error("删除个人评论失败");
    }

}
