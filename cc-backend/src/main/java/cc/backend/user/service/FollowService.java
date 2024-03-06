package cc.backend.user.service;

import cc.backend.entity.Follow;
import cc.backend.entity.SearchData;
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
    SearchData<Follow> getFollowUsers(String tokenInfo, SearchData<Follow> searchData,FollowStatus followStatus);
    SearchData<Follow> getFanUsers(String tokenInfo, SearchData<Follow> searchData,FollowStatus followStatus);
}
