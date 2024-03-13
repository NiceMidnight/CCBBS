package cc.backend.entity.forpost;

import cc.backend.enums.TopicStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 帖子主题
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
@Data
@TableName("topic_for_post")
public class TopicForPost {
    /**
     * 主题Id
     */
    @TableId(value = "topic_id",type = IdType.AUTO)
    private Integer topicId;
    /**
     * 主题名称
     */
    private String topicName;
    /**
     * 创建者id
     */
    private Integer createdId;
    /**
     * 创建者名称
     */
    @TableField(exist = false)
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新者id
     */
    private Integer updatedId;
    /**
     * 更新者名称
     */
    @TableField(exist = false)
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 主题状态
     */
    private TopicStatus topicStatus;
    /**
     * 主题背景颜色
     */
    private String topicColor;
}
