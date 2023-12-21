package cc.backend.manager.mapper;


import cc.backend.entity.SysImageResource;
import cc.backend.enums.SysImgStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @FileName ImagesMapper
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-21
 **/
@Mapper
public interface ImagesMapper extends BaseMapper<SysImageResource> {
    IPage<SysImageResource> selectAllImg(IPage<SysImageResource> iPage, @Param("type") Integer type, @Param("userName") String userName);
    int updateSysImgStatus(@Param("id") int id, @Param("visible")SysImgStatus sysImgStatus);
}
