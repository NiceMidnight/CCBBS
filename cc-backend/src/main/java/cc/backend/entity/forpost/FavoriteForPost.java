package cc.backend.entity.forpost;

import cc.backend.enums.FavoriteForPostStatus;
import cc.backend.enums.FavoritePostStatus;
import cc.backend.enums.PostStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/19
 */
@Data
@TableName("favorite_for_post")
public class FavoriteForPost {
    /**
     * 收藏id
     */
    private Integer favoriteId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 帖子id
     */
    private Integer postId;
    /**
     * 收藏备注
     */
    private String favoriteNote;
    /**
     * 收藏夹id
     */
    private Integer folderId;
    /**
     * 收藏时间
     */
    private Date timestamp;
    /**
     * 收藏状态
     */
    private FavoriteForPostStatus favoriteStatus;
    /**
     * 收藏的帖子状态
     */
    private FavoritePostStatus favoritePostStatus;
}
