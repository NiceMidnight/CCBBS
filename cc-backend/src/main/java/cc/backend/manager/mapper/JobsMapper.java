package cc.backend.manager.mapper;

import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/11
 */
public interface JobsMapper extends BaseMapper<Job> {
    IPage<Job> selectAllJobMessage(IPage<Job> iPage, Job job,
                                   @Param("startTime") LocalDateTime startTime,
                                   @Param("endTime")LocalDateTime endTime);

    int updateJobStatus(@Param("jobId")Integer jobId, @Param("jobStatus")JobStatus jobStatus);
}
