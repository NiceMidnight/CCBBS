package cc.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 日活跃用户日志
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
@Data
@TableName("daily_unique_visitors")
public class DailyUniqueVisitors {
    @TableId(value = "log_id",type = IdType.AUTO)
    private Integer logId;

    private Integer userId;


    private Date firstAccessTime;

    private String ip;
}
