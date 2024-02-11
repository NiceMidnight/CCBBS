package cc.backend.entity;

import cc.backend.enums.CommentStatusForCompliance;
import cc.backend.enums.CommentStatusForUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Description 评论
 * @Author Tiamo_null
 * @Date 2023/11/22
 */
@Data
@TableName("comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    /**
     * 评论ID
     */
    @TableId(value = "comment_id",type = IdType.AUTO)
    private Integer commentId;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 帖子ID
     */
    private Integer postId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String nickName;
    /**
     * 用户头像
     */
    @TableField(exist = false)
    private String userHead;
    /**
     * 父评论id
     */
    private Integer parentCommentId;
    /**
     * 创建日期
     */
    private Date createdAt;
    /**
     * 状态是否已查看
     */
    private CommentStatusForUser statusForUser;
    /**
     * 状态是否合规
     */
    private CommentStatusForCompliance statusForCompliance;
    /**
     * 子评论集
     */
    @TableField(exist = false)
    private List<Comments> childCommentsList;
    /**
     * 回复数
     */
    @TableField(exist = false)
    private int replyCount;
}
