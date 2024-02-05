package cc.backend.user.mapper;

import cc.backend.entity.forpost.LikesForPost;
import cc.backend.enums.DislikesForPostStatus;
import cc.backend.enums.LikesForPostStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/5
 */
public interface LikesForPostMapper extends BaseMapper<LikesForPost> {
    LikesForPostStatus getLikeForPostStatus(@Param("userId") Integer userId,@Param("postId") Integer postId);
    int updateLikeStatus(LikesForPost likesForPost);
}
