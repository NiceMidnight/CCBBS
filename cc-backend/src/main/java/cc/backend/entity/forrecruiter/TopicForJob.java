package cc.backend.entity.forrecruiter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description 招聘主题
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@TableName("topic_for_job")
@Data
public class TopicForJob {
    @TableId(value = "topic_id",type = IdType.AUTO)
    private Integer topicId;

    private String topicName;

    private String topicColor;

    @TableField(exist = false)
    private Long count;
}
