package cc.backend.user.mapper;

import cc.backend.entity.forrecruiter.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
    IPage<Job> selectJobMessage(IPage<Job> iPage,Job job);
//    @Param("jobVisibility") JobVisibility jobVisibility,
//    @Param("jobStatus") JobStatus jobStatus
}
