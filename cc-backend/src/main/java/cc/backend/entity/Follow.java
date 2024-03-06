package cc.backend.entity;

import cc.backend.enums.FollowStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/21
 */
@Data
@TableName("follow")
public class Follow {

    /**
     * 关注者（本人）
     */
//    @TableId(value = "follower_id", type = IdType.INPUT)
    private Integer followerId;
    /**
     * 关注者昵称
     */
    @TableField(exist = false)
    private String followerNickName;
    /**
     * 关注者头像
     */
    @TableField(exist = false)
    private String followerHead;
    /**
     * 被关注者
     */
//    @TableId(value = "following_id", type = IdType.INPUT)
    private Integer followingId;
    /**
     * 被关注者昵称
     */
    @TableField(exist = false)
    private String followingNickName;
    /**
     * 被关注者头像
     */
    @TableField(exist = false)
    private String followingHead;
    /**
     * 关注时间
     */
    private Date followDate;
    /**
     * 关注状态
     */
    private FollowStatus followStatus;
}
