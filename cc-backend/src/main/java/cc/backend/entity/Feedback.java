package cc.backend.entity;

import cc.backend.enums.FeedbackReminderStatus;
import cc.backend.enums.FeedbackStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
@Data
@TableName("feedback")
public class Feedback {
    private Integer feedbackId;
    private Integer feedbackType;
    private String feedbackContent;
    private Integer creatorId;
    private Date createTime;
    private Integer handlerId;
    private Date handleTime;
    private FeedbackStatus feedbackStatus;
    private FeedbackReminderStatus reminderStatus;
}
