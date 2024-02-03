package cc.backend.user.service.impl;

import cc.backend.common.Token;
import cc.backend.entity.Follow;
import cc.backend.enums.FollowStatus;
import cc.backend.user.mapper.FollowMapper;
import cc.backend.user.service.FollowService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/22
 */
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private Token token;
    @Autowired
    private FollowMapper followMapper;


    /**
     * @description TODO 获取关注状态
     * @param tokenInfo
     * @return: boolean
     * @param followingId
     */
    @Override
    public boolean getFollowStatus(String tokenInfo, Integer followingId) {
        int followerId = token.getUserId(tokenInfo);
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("follower_id",followerId).eq("following_id",followingId);
        Follow follow = followMapper.selectOne(followQueryWrapper);
        if (follow != null) return follow.getFollowStatus() == FollowStatus.Follow;
        return false;
    }

    /**
     * @description TODO 关注用户
     * @param tokenInfo
     * @param followingId
     * @return: boolean
     */
    @Override
    public boolean following(String tokenInfo, Integer followingId) {
        int followerId = token.getUserId(tokenInfo);
        QueryWrapper<Follow> followQueryWrapper = new QueryWrapper<>();
        followQueryWrapper.eq("follower_id",followerId).eq("following_id",followingId);
        Follow selectOneFollow = followMapper.selectOne(followQueryWrapper);
        if (selectOneFollow == null) {
            Follow follow = new Follow();
            follow.setFollowerId(followerId);
            follow.setFollowingId(followingId);
            follow.setFollowDate(new Date());
            return followMapper.insertFollow(follow) > 0;
        }
        return false;
    }

    /**
     * @description TODO 更改关注状态
     * @param tokenInfo
     * @param followingId
     * @return: boolean
     */
    @Override
    public boolean updateFollowStatus(String tokenInfo, Integer followingId,FollowStatus followStatus) {
        int followerId = token.getUserId(tokenInfo);
        return followMapper.updateFollowStatus(followerId,followingId,followStatus,new Date()) > 0;
    }
}
