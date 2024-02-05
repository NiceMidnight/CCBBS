package cc.backend.user.service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/4
 */
public interface LikesForPostService
{
    boolean getLikeForPostStatus(String tokenInfo,Integer postId);
    boolean giveALike(String tokenInfo,Integer postId);
    boolean cancelALike(String tokenInfo,Integer postId);
}
