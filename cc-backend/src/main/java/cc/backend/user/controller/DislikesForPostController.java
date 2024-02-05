package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.user.service.impl.DislikesForPostServiceImpl;
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
}
