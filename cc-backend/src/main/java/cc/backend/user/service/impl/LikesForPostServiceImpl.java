package cc.backend.user.service.impl;

import cc.backend.common.Token;
import cc.backend.entity.forpost.LikesForPost;
import cc.backend.enums.LikesForPostStatus;
import cc.backend.user.mapper.LikesForPostMapper;
import cc.backend.user.service.LikesForPostService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/4
 */
@Service
public class LikesForPostServiceImpl implements LikesForPostService
{

    @Autowired
    private LikesForPostMapper likesForPostMapper;

    @Autowired
    private Token token;

    /**
     * @description TODO 获取帖子点赞状态
     * @param tokenInfo
     * @param postId
     * @return: boolean
     */
    @Override
    public boolean getLikeForPostStatus(String tokenInfo,Integer postId)
    {
        int userId = token.getUserId(tokenInfo);
        LikesForPostStatus isLike = likesForPostMapper.getLikeForPostStatus(userId, postId);
        if (isLike != null)
        {
            return LikesForPostStatus.Liked.equals(isLike);
        }
        return false;
    }

    /**
     * @description TODO 点赞
     * @param tokenInfo
     * @param postId
     * @return: boolean
     */
    @SneakyThrows
    @Override
    public boolean giveALike(String tokenInfo, Integer postId) {
        int userId = token.getUserId(tokenInfo);
        LikesForPost likesForPost = new LikesForPost();
        likesForPost.setPostId(postId);
        likesForPost.setUserId(userId);
        likesForPost.setTimestamp(new Date());
        likesForPost.setLikeStatus(LikesForPostStatus.Liked);
        int updateLikeStatus = likesForPostMapper.updateLikeStatus(likesForPost);
        if (updateLikeStatus > 0 )
        {
            return true;
        }
        return likesForPostMapper.insert(likesForPost) > 0;
    }

    /**
     * @description TODO 取消点赞
     * @param tokenInfo
     * @param postId
     * @return: boolean
     */
    @SneakyThrows
    @Override
    public boolean cancelALike(String tokenInfo, Integer postId) {
        int userId = token.getUserId(tokenInfo);
        LikesForPost likesForPost = new LikesForPost();
        likesForPost.setPostId(postId);
        likesForPost.setUserId(userId);
        likesForPost.setTimestamp(new Date());
        likesForPost.setLikeStatus(LikesForPostStatus.Unliked);
        return likesForPostMapper.updateLikeStatus(likesForPost) > 0;
    }
}
