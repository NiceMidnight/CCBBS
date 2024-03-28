package cc.backend.entity;

import cc.backend.enums.SysImgStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @FileName SysImageResource
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-21
 **/
@TableName("sys_images")
@Data
public class SysImageResource {
    /**
     * 系统图片id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 图片名
     */
    private String imgName;
    /**
     * 图片路径
     */
    private String imgPath;
    /**
     * 发布管理员id
     */
    private Long userId;
    /**
     * 用户名
     * 使用mybatis-plus将该字段名剔除
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 设置图片状态可见/不可见
     */
    private SysImgStatus visible;
    /**
     * 类型id
     */
    private Integer type;
    /**
     * 类型名称，用处
     */
    @TableField(exist = false)
    private String typeName;
    /**
     * 字典颜色
     */
    @TableField(exist = false)
    private String dictColor;
}
