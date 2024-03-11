package cc.backend.entity.forrecruiter;

import cc.backend.enums.ApplyStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 申请权限
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@TableName("apply_recruiter")
@Data
public class ApplyRecruiter {
    /**
     * 申请id
     */
    @TableId(value = "apply_id",type = IdType.AUTO)
    private Integer applyId;

    private Integer userId;

    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private String userName;

    private Date createdAt;

    private ApplyStatus applyStatus;

    private Integer handlingUserId;

    @TableField(exist = false)
    private String handlingNickName;

    @TableField(exist = false)
    private String handlingUserName;

    private String notes;

    private Date handlingAt;

    private String truthName;

    private String studentId;

    private String phone;
}
