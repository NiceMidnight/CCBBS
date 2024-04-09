package cc.backend.user.service.impl;

import cc.backend.entity.forpost.FavoriteForPost;
import cc.backend.enums.FavoriteStatus;
import cc.backend.user.mapper.FavoriteForPostMapper;
import cc.backend.user.service.FavoriteForPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/4/9
 */
@Service
public class FavoriteForPostServiceImpl implements FavoriteForPostService {
    @Autowired
    private FavoriteForPostMapper favoriteForPostMapper;
    @Override
    public boolean getFavoriteForPostStatus(Integer postId, Integer userId)
    {
        FavoriteStatus favoriteForPostStatus = favoriteForPostMapper.getFavoriteForPostStatus(userId, postId);
        if (favoriteForPostStatus != null)
        {
            return FavoriteStatus.Favorite.equals(favoriteForPostStatus);
        }
        return false;
    }

    @Override
    public boolean giveAFavorite(Integer userId, Integer postId) {
        FavoriteForPost favoriteForPost = new FavoriteForPost();
        favoriteForPost.setPostId(postId);
        favoriteForPost.setUserId(userId);
        favoriteForPost.setTimestamp(new Date());
        favoriteForPost.setFavoriteStatus(FavoriteStatus.Favorite);
        int updateFavoriteStatus = favoriteForPostMapper.updateFavoriteStatus(favoriteForPost);
        if (updateFavoriteStatus > 0)
        {
            return true;
        }
        return favoriteForPostMapper.insert(favoriteForPost) > 0;
    }

    @Override
    public boolean cancelAFavorite(Integer userId, Integer postId) {
        FavoriteForPost favoriteForPost = new FavoriteForPost();
        favoriteForPost.setPostId(postId);
        favoriteForPost.setUserId(userId);
        favoriteForPost.setTimestamp(new Date());
        favoriteForPost.setFavoriteStatus(FavoriteStatus.Unfavorite);
        return favoriteForPostMapper.updateFavoriteStatus(favoriteForPost) > 0;
    }
}
