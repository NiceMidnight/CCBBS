package cc.backend.user.service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/5
 */
public interface DislikesForPostService {
    boolean getDislikeForPostStatus(String tokenInfo,Integer postId);
}
