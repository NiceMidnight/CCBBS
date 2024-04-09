package cc.backend.user.mapper;

import cc.backend.entity.forpost.FavoriteForPost;
import cc.backend.entity.forpost.LikesForPost;
import cc.backend.enums.FavoriteStatus;
import cc.backend.enums.LikesForPostStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/5
 */
public interface FavoriteForPostMapper extends BaseMapper<FavoriteForPost> {
    FavoriteStatus getFavoriteForPostStatus(@Param("userId") Integer userId, @Param("postId") Integer postId);
    int updateFavoriteStatus(FavoriteForPost favoriteForPost);
}
