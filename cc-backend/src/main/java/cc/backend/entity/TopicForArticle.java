package cc.backend.entity;

import cc.backend.enums.TopicStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 公告文章主题
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
@Data
@TableName("topic_for_article")
public class TopicForArticle {
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
     * 更新时间
     */
    private Date createdTime;
    /**
     * 主题状态
     */
    private TopicStatus topicStatus;
    /**
     * 主题颜色
     */
    private String topicColor;
}
