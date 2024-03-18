package cc.backend.entity.feedback;

import cc.backend.enums.TopicStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 反馈类型
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
@Data
@TableName("topic_for_feedback")
public class TopicForFeedback {
    @TableId(value = "topic_id",type = IdType.AUTO)
    private Integer topicId;

    private String topicName;

    private Integer userId;

    @TableField(exist = false)
    private String userName;

    private Date createdTime;

    private String topicColor;

    private TopicStatus topicStatus;
}
