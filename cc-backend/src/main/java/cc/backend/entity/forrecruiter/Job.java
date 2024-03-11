package cc.backend.entity.forrecruiter;

import cc.backend.enums.JobStatus;
import cc.backend.enums.JobVisibility;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 招聘信息
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@TableName("job")
@Data
public class Job {
    /**
     * 招聘id
     */
    @TableId(value = "job_id",type = IdType.AUTO)
    private Integer jobId;

    private String jobTitle;

    private String jobContent;

    private Integer topicId;

    @TableField(exist = false)
    private String topicName;

    @TableField(exist = false)
    private String topicColor;

    private Integer userId;

    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String userHead;

    private Date createdAt;

    private JobStatus jobStatus;

    private JobVisibility jobVisibility;

    private Integer viewCount;
}
