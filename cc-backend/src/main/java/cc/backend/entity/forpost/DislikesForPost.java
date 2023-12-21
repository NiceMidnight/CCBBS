package cc.backend.entity.forpost;

import cc.backend.enums.DislikesForPostStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 踩贴
 * @Author Tiamo_null
 * @Date 2023/12/19
 */
@Data
@TableName("dislikes_for_post")
public class DislikesForPost {
    /**
     * 踩id
     */
    @TableId(value = "like_id",type = IdType.AUTO)
    private Integer dislikeId;
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
     * 是否踩状态
     */
    private DislikesForPostStatus dislikeStatus;
    /**
     * 操作时间
     */
    private Date timestamp;
}
