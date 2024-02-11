package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.Post;
import cc.backend.user.service.impl.DislikesForPostServiceImpl;
import cc.backend.user.service.impl.PostServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/5
 */
@RestController
@RequestMapping("/user/dislikesForPost")
public class DislikesForPostController {
    @Autowired
    private DislikesForPostServiceImpl dislikesForPostService;
    @Autowired
    private PostServiceImpl postService;
    /**
     * @description TODO 获取帖子踩状态
     * @param postId
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getDislikesForPostStatus")
    public Result getDislikesForPostStatus(@RequestParam("postId")Integer postId, @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isDislike = dislikesForPostService.getDislikeForPostStatus(tokenInfo, postId);
        if (isDislike)
        {
            return Result.successCDM(true,"已点赞");
        }
        return Result.successCDM(false,"未点赞");
    }
    /**
     * @description TODO 踩贴
     * @param tokenInfo
     * @param postId
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @RequestMapping("/giveADislike")
    public Result giveADislike(@RequestHeader("Authorization")String tokenInfo,@RequestParam("postId")Integer postId)
    {
        boolean isGive = dislikesForPostService.giveADislike(tokenInfo, postId);
        if (isGive)
        {
            Post post = postService.updatePostDislikesCount(postId,true);
            if (post != null) {
                return Result.successCDM(post.getDislikeCount(),"点赞成功");
            }
            return Result.error("点赞成功，但更新帖子点赞数失败");
        }
        return Result.error("踩贴失败");
    }
    /**
     * @description TODO 取消踩贴
     * @param tokenInfo
     * @param postId
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @RequestMapping("/cancelADislike")
    public Result cancelADislike(@RequestHeader("Authorization")String tokenInfo,@RequestParam("postId")Integer postId)
    {
        boolean isCancel = dislikesForPostService.cancelADislike(tokenInfo, postId);
        if (isCancel)
        {
            Post post = postService.updatePostDislikesCount(postId,false);
            if (post != null) {
                return Result.successCDM(post.getDislikeCount(),"取消踩贴成功");
            }
            return Result.error("取消踩贴成功，但更新帖子踩贴数失败");
        }
        return Result.error("取消踩贴失败");
    }
}
