package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.Post;
import cc.backend.user.service.impl.LikesForPostServiceImpl;
import cc.backend.user.service.impl.PostServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/4
 */
@RestController
@RequestMapping("/user/likesForPost")
public class LikesForPostController {
    @Autowired
    private LikesForPostServiceImpl likesForPostService;

    @Autowired
    private PostServiceImpl postService;
    /**
     * @description TODO 查看是否点赞
     * @param postId
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getLikesForPostStatus")
    public Result getLikesForPostStatus(@RequestParam("postId")Integer postId, @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isLike = likesForPostService.getLikeForPostStatus(tokenInfo, postId);
        if (isLike)
        {
            return Result.successCDM(true,"已点赞");
        }
        return Result.successCDM(false,"未点赞");
    }

    /**
     * @description TODO 点赞
     * @param tokenInfo
     * @param postId
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @RequestMapping("/giveALike")
    public Result giveALike(@RequestHeader("Authorization")String tokenInfo,@RequestParam("postId")Integer postId)
    {
        boolean isGive = likesForPostService.giveALike(tokenInfo, postId);
        if (isGive)
        {
            Post post = postService.updatePostLikesCount(postId,true);
            if (post != null) {
                return Result.successCDM(post.getLikeCount(),"点赞成功");
            }
            return Result.error("点赞成功，但更新帖子点赞数失败");
        }
        return Result.error("点赞失败");
    }

    /**
     * @description TODO 取消点赞
     * @param tokenInfo
     * @param postId
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @RequestMapping("/cancelALike")
    public Result cancelALike(@RequestHeader("Authorization")String tokenInfo,@RequestParam("postId")Integer postId)
    {
        boolean isCancel = likesForPostService.cancelALike(tokenInfo, postId);
        if (isCancel)
        {
            Post post = postService.updatePostLikesCount(postId,false);
            if (post != null) {
                return Result.successCDM(post.getLikeCount(),"取消赞成功");
            }
            return Result.error("取消赞成功，但更新帖子点赞数失败");
        }
        return Result.error("取消赞失败");
    }
}
