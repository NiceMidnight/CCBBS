package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.user.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getCommentCount")
    public Result getCommentCount(@RequestParam("postId") int postId) {
        int commentCount = commentService.getCommentCount(postId);
        return Result.successCDM(commentCount,"获取评论数量成功");
    }

}
