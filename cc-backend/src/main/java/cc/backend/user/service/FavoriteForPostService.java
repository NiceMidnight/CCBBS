package cc.backend.user.service;

import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.FavoriteForPost;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/4/9
 */
public interface FavoriteForPostService {
    boolean getFavoriteForPostStatus(Integer postId,Integer userId);
    boolean giveAFavorite(Integer userId,Integer postId);
    boolean cancelAFavorite(Integer userId,Integer postId);
    SearchData<FavoriteForPost> getFavoritePost(SearchData<FavoriteForPost> searchData,Integer userId);
}
