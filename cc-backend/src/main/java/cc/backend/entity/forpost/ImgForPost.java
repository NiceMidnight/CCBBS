package cc.backend.entity.forpost;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/4
 */
@Data
@TableName("img_for_post")
public class ImgForPost {
    /**
     * 文件id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String imgId;
    /**
     * 文件名
     */
    private String imgName;
    /**
     * 文件路径
     */
    private String imgPath;
    /**
     * 文件创建时间
     */
    private String createdTime;
    /**
     * 帖子id
     */
    private Integer postId;
}
