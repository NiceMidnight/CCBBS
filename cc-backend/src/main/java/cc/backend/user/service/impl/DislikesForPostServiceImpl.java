package cc.backend.user.service.impl;

import cc.backend.common.Token;
import cc.backend.enums.DislikesForPostStatus;
import cc.backend.enums.LikesForPostStatus;
import cc.backend.user.mapper.DislikesForPostMapper;
import cc.backend.user.service.DislikesForPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
