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
public class FileForPost {
    /**
     * 文件id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件创建时间
     */
    private String createdTime;
    /**
     * 帖子id
     */
    private Integer postId;
}
