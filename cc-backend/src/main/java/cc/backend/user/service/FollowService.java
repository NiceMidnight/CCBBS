package cc.backend.user.service;

import cc.backend.enums.FollowStatus;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/22
 */
public interface FollowService {
    boolean getFollowStatus(String tokenInfo,Integer followingId);
    boolean following(String tokenInfo,Integer followingId);
    boolean updateFollowStatus(String tokenInfo, Integer followingId, FollowStatus followStatus);
}
