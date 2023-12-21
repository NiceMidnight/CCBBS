package cc.backend.entity.forpost;

import cc.backend.enums.LikesForPostStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 点赞贴
 * @Author Tiamo_null
 * @Date 2023/12/19
 */
@Data
@TableName("likes_for_post")
public class LikesForPost {
    /**
     * 点赞id
     */
    @TableId(value = "like_id",type = IdType.AUTO)
    private Integer likeId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 帖子id
     */
    private Integer postId;
    /**
     * 是否点赞状态
     */
    private LikesForPostStatus like_status;
    /**
     * 操作时间
     */
    private Date timestamp;
}
