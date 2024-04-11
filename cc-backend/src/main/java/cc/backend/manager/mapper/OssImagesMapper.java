package cc.backend.manager.mapper;

import cc.backend.entity.OssImages;
import cc.backend.entity.Post;
import cc.backend.enums.ImageStatus;
import cc.backend.enums.PostStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/19
 */
public interface OssImagesMapper extends BaseMapper<OssImages> {
    IPage<OssImages> selectOssImages(IPage<OssImages> iPage, OssImages ossImages,
                              @Param("startTime") LocalDateTime startTime,
                              @Param("endTime") LocalDateTime endTime,
                              @Param("imageStatus") ImageStatus imageStatus);
    int updateOssImageStatus(@Param("imgId")int imgId,@Param("imageStatus") ImageStatus imageStatus);
}
