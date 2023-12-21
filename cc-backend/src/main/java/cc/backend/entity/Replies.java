package cc.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/21
 */
@Data
@TableName("replies")
public class Replies {
    /**
     * 回复ID
     */
    @TableId(value = "reply_id",type = IdType.AUTO)
    private Integer replyId;
    /**
     * 回复内容
     */
    private String replyContent;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 评论ID
     */
    private Integer commentId;
    /**
     * 创建日期
     */
    private Date createdAt;  //日期
}
