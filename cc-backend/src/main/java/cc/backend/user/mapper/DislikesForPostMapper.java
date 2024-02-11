package cc.backend.user.mapper;

import cc.backend.entity.forpost.DislikesForPost;
import cc.backend.enums.DislikesForPostStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/5
 */
public interface DislikesForPostMapper extends BaseMapper<DislikesForPost> {
    DislikesForPostStatus getDislikeForPostStatus(@Param("userId") Integer userId, @Param("postId") Integer postId);
    int updateDislikeStatus(DislikesForPost dislikesForPost);
}
