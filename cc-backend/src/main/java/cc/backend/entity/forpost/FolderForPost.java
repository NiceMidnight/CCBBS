package cc.backend.entity.forpost;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/19
 */
@Data
@TableName("folder_for_post")
public class FolderForPost {
    /**
     * 收藏夹id
     */
    private Integer folderId;
    /**
     * 收藏夹名
     */
    private String folderName;
    /**
     * 用户id
     */
    private Long uerId;
    /**
     * 收藏夹创建时间
     */
    private String createdTime;
}
