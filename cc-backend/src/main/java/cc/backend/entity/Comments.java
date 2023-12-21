package cc.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/22
 */
@Data
@TableName("comments")
public class Comments {
    /**
     * 评论ID
     */
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
    private String userName;
    /**
     * 创建日期
     */
    private Date createdAt;  //日期
}
