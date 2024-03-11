package cc.backend.manager.service;

import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/11
 */
public interface JobsService {
    SearchData<Job> getJobMessage(SearchData<Job> searchData, LocalDateTime startTime, LocalDateTime endTime);

    boolean updateJobStatus(Integer jobId, JobStatus jobStatus);

    Job jobView(Integer jobId);
}
