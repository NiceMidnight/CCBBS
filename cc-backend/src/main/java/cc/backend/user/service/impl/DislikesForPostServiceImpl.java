package cc.backend.user.service.impl;

import cc.backend.common.Token;
import cc.backend.entity.forpost.DislikesForPost;
import cc.backend.enums.DislikesForPostStatus;
import cc.backend.user.mapper.DislikesForPostMapper;
import cc.backend.user.service.DislikesForPostService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/5
 */
@Service
public class DislikesForPostServiceImpl implements DislikesForPostService {
    @Autowired
    private DislikesForPostMapper dislikesForPostMapper;

    @Autowired
    private Token token;
    /**
     * @description TODO 获取帖子踩状态
     * @param tokenInfo
     * @param postId
     * @return: boolean
     */
    @Override
    public boolean getDislikeForPostStatus(String tokenInfo,Integer postId)
    {
        int userId = token.getUserId(tokenInfo);
        DislikesForPostStatus isDislike = dislikesForPostMapper.getDislikeForPostStatus(userId, postId);
        if (isDislike != null)
        {
            return DislikesForPostStatus.Disliked.equals(isDislike);
        }
        return false;
    }

    /**
     * @description TODO 踩贴
     * @param tokenInfo
     * @param postId
     * @return: boolean
     */
    @SneakyThrows
    @Override
    public boolean giveADislike(String tokenInfo, Integer postId) {
        int userId = token.getUserId(tokenInfo);
        DislikesForPost dislikesForPost = new DislikesForPost();
        dislikesForPost.setPostId(postId);
        dislikesForPost.setUserId(userId);
        dislikesForPost.setTimestamp(new Date());
        dislikesForPost.setDislikeStatus(DislikesForPostStatus.Disliked);
        int updateDislikeStatus = dislikesForPostMapper.updateDislikeStatus(dislikesForPost);
        if (updateDislikeStatus > 0)
        {
            return true;
        }

        return dislikesForPostMapper.insert(dislikesForPost) > 0;

    }

    /**
     * @description TODO 取消踩
     * @param tokenInfo
     * @param postId
     * @return: boolean
     */
    @Override
    public boolean cancelADislike(String tokenInfo, Integer postId) {
        int userId = token.getUserId(tokenInfo);
        DislikesForPost dislikesForPost = new DislikesForPost();
        dislikesForPost.setPostId(postId);
        dislikesForPost.setUserId(userId);
        dislikesForPost.setTimestamp(new Date());
        dislikesForPost.setDislikeStatus(DislikesForPostStatus.Undisliked);
        return dislikesForPostMapper.updateDislikeStatus(dislikesForPost) > 0;
    }
}
