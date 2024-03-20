package cc.backend.entity.feedback;

import cc.backend.enums.FeedbackReminderStatus;
import cc.backend.enums.FeedbackStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 反馈信息
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
@Data
@TableName("feedback")
public class Feedback {
    @TableId(value = "feedback_id",type = IdType.AUTO)
    private Integer feedbackId;

    private Integer topicId;

    @TableField(exist = false)
    private String topicName;
    @TableField(exist = false)
    private String topicColor;

    private String feedbackContent;

    private Integer creatorId;
    @TableField(exist = false)
    private String creatorName;
    @TableField(exist = false)
    private String creatorHead;
    private Date createdTime;

    private Integer handlerId;
    @TableField(exist = false)
    private String handlerName;
    @TableField(exist = false)
    private String handlerHead;

    private Date handleTime;

    private FeedbackStatus feedbackStatus;

    private FeedbackReminderStatus reminderStatus;

}
