package cc.backend.entity;

import cc.backend.enums.ImageStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 图片管理
 * @Author Tiamo_null
 * @Date 2024/2/19
 */
@Data
@TableName("images")
public class OssImages {
    /**
     * 图片id
     */
    @TableId(value = "img_id",type = IdType.AUTO)
    private Integer imgId;
    /**
     * 图片名称
     */
    private String imgName;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 上传时间
     */
    private Date uploadTime;
    /**
     * 图片状态
     */
    private ImageStatus imgStatus;
}
