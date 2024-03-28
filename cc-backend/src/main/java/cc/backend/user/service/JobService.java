package cc.backend.user.service;

import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;
import cc.backend.enums.JobVisibility;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
public interface JobService {
    SearchData<Job> getJobMessage(SearchData<Job> searchData);
    boolean addJobMessage(Job job,String tokenInfo);
    Job getOneJobMessage(Integer jobId, JobStatus jobStatus, JobVisibility jobVisibility);
    boolean addViewCount(Integer jobId);
}
