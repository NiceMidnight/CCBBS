package cc.backend.manager.service.impl;

import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.ApplyRecruiter;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;
import cc.backend.manager.mapper.JobsMapper;
import cc.backend.manager.service.JobsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/11
 */
@Service
public class JobsServiceImpl implements JobsService {
    @Autowired
    private JobsMapper jobsMapper;
    @Override
    public SearchData<Job> getJobMessage(SearchData<Job> searchData, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Job> iPage = new Page<>(searchData.getPageNum(), searchData.getPageSize());
        jobsMapper.selectAllJobMessage(iPage,searchData.getData(),startTime,endTime);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public boolean updateJobStatus(Integer jobId, JobStatus jobStatus) {
        return jobsMapper.updateJobStatus(jobId,jobStatus) > 0;
    }

    @Override
    public Job jobView(Integer jobId) {
        return jobsMapper.selectById(jobId);
    }

}
