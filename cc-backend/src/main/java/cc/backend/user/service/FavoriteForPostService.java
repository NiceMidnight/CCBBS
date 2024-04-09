package cc.backend.user.service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/4/9
 */
public interface FavoriteForPostService {
    boolean getFavoriteForPostStatus(Integer postId,Integer userId);
    boolean giveAFavorite(Integer userId,Integer postId);
    boolean cancelAFavorite(Integer userId,Integer postId);
}
