package cc.backend.user.mapper;

import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;
import cc.backend.enums.JobVisibility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {
    IPage<Job> selectJobMessage(IPage<Job> iPage,Job job);
    Job selectOneByJobId(@Param("jobId") Integer jobId,
                         @Param("jobStatus")JobStatus jobStatus,
                         @Param("jobVisibility")JobVisibility jobVisibility);
}
