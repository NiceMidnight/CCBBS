package cc.backend.entity;

import cc.backend.enums.PostStatus;
import cc.backend.enums.PostVisibility;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/17
 */
@Data
@TableName("post")
public class Post {
    /**
     * 帖子ID
     */
    @TableId(value = "post_id",type = IdType.AUTO)
    private Integer postId;
    /**
     * 帖子所属主题id
     */
    private Integer topicId;
    /**
     * 帖子标题名称
     */
    @TableField(exist = false)
    private String topicName;
    /**
     * 帖子标题
     */
    private String postTitle;
    /**
     * 帖子内容
     */
    private String postContent;
    /**
     * 管理员ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nickName;
    /**
     * 创建日期
     */
    private Date createdAt;
    /**
     * 是否合规
     */
    private PostStatus postStatus;
    /**
     * 用户设置是否可见
     */
    private PostVisibility postVisibility;
    /**
     * 帖子阅读量
     */
    private Integer viewCount;
}
