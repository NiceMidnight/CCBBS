package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.enums.FollowStatus;
import cc.backend.user.service.impl.FollowServiceImpl;
import cc.backend.user.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/22
 */
@RestController
@RequestMapping("/user/follow")
public class FollowController {
    @Autowired
    private FollowServiceImpl followService;
    @Autowired
    private UserServiceImpl userService;
    /***
     * @description TODO 获取关注状态
     * @param: @param tokenInfo
     * @param followingId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getFollowStatus")
    public Result getFollowStatus(@RequestHeader("Authorization") String tokenInfo, @RequestParam("followingId")Integer followingId) {
        boolean isFollow = followService.getFollowStatus(tokenInfo, followingId);
        if (isFollow) return Result.successCDM(true,"获取对用户"+followingId+"的关注状态成功（已关注）");
        else return Result.successCDM(false,"获取对用户"+followingId+"的关注状态成功（未关注）");
    }
    /**
     * @description TODO 关注用户
     * @param: @param tokenInfo
     * @param followingId
     * @return: cc.backend.common.Result
     */
    @SneakyThrows
    @GetMapping("/following")
    public Result following(@RequestHeader("Authorization") String tokenInfo, @RequestParam("followingId")Integer followingId) {
        boolean isFollow = followService.following(tokenInfo, followingId);
        if (isFollow)
        {
            boolean isUpdateUserFans = userService.updateUserFans(followingId, true);
            if (isUpdateUserFans)
            {
                return Result.successCM("关注成功");
            }
            return Result.error("关注失败");
        }
        else
        {
            boolean isUpdatedFollow = followService.updateFollowStatus(tokenInfo, followingId, FollowStatus.Follow);
            if (isUpdatedFollow)
            {
                boolean isUpdateUserFans = userService.updateUserFans(followingId,true);
                if (isUpdateUserFans)
                {
                    return Result.successCM("关注成功");
                }
                return Result.error("关注失败");
            }
        }
        return Result.error("关注失败");
    }

    /**
     * @description TODO 取消关注用户
     * @param: @param tokenInfo
     * @param followingId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/cancelFollow")
    public Result cancelFollow(@RequestHeader("Authorization") String tokenInfo,@RequestParam("followingId")Integer followingId)
    {
        boolean isFollow = followService.updateFollowStatus(tokenInfo, followingId, FollowStatus.Unfollowed);
        if (isFollow)
        {
            boolean isUpdateUserFans = userService.updateUserFans(followingId, false);
            if (isUpdateUserFans)
            {
                return Result.successCM("取消关注成功");
            }
        }
        return Result.error("取消关注失败");
    }
}
