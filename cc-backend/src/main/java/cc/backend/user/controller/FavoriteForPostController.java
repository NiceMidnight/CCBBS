package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.common.token.Token;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.FavoriteForPost;
import cc.backend.user.service.impl.FavoriteForPostServiceImpl;
import cc.backend.user.service.impl.PostServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/4/9
 */
@RestController
@RequestMapping("/user/favoriteForPost")
public class FavoriteForPostController {
    @Autowired
    private FavoriteForPostServiceImpl favoriteForPostService;
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private Token token;
    @GetMapping("/getFavoriteForPostStatus")
    public Result getFavoriteForPostStatus(@RequestParam("postId")Integer postId, @RequestHeader("Authorization")String tokenInfo)
    {
        int userId = token.getUserId(tokenInfo);
        boolean isFavorite = favoriteForPostService.getFavoriteForPostStatus(postId, userId);
        if (isFavorite)
        {
            return Result.successCDM(true,"已收藏");
        }
        return Result.successCDM(false,"未收藏");
    }
    /**
     * TODO 收藏
     */
    @RequestMapping("/giveAFavorite")
    public Result giveAFavorite(@RequestHeader("Authorization")String tokenInfo,@RequestParam("postId")Integer postId)
    {
        int userId = token.getUserId(tokenInfo);
        boolean isGive = favoriteForPostService.giveAFavorite(userId, postId);
        if (isGive)
        {
            Post post = postService.updatePostFavoritesCount(postId,true);
            if (post != null) {
                return Result.successCDM(post.getFavoriteCount(),"点赞成功");
            }
            return Result.error("收藏成功，但更新帖子收藏数失败");
        }
        return Result.error("收藏失败");
    }

    /**
     * TODO 取消收藏
     */
    @RequestMapping("/cancelAFavorite")
    public Result cancelAFavorite(@RequestHeader("Authorization")String tokenInfo,@RequestParam("postId")Integer postId)
    {
        int userId = token.getUserId(tokenInfo);
        boolean isCancel = favoriteForPostService.cancelAFavorite(userId, postId);
        if (isCancel)
        {
            Post post = postService.updatePostFavoritesCount(postId,false);
            if (post != null) {
                return Result.successCDM(post.getFavoriteCount(),"取消赞成功");
            }
            return Result.error("取消收藏成功，但更新帖子收藏数失败");
        }
        return Result.error("取消收藏失败");
    }

    @RequestMapping("/getFavoritePost")
    public Result getFavoritePost(@RequestBody SearchData<FavoriteForPost> queryForm,@RequestHeader("Authorization")String tokenInfo)
    {
        int userId = token.getUserId(tokenInfo);
        SearchData<FavoriteForPost> favoritePost = favoriteForPostService.getFavoritePost(queryForm,userId);
        return Result.successCDM(favoritePost,"获取当前用户收藏帖子成功");
    }
}
