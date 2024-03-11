package cc.backend.user.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;
import cc.backend.enums.JobVisibility;
import cc.backend.user.mapper.JobMapper;
import cc.backend.user.service.JobService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@Service
public final class JobServiceImpl implements JobService {
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private Token token;
    /**
     * TODO 获取招聘信息
     */
    @Override
    public SearchData<Job> getJobMessage(SearchData<Job> searchData) {
        IPage<Job> jobIPage = new Page<>(searchData.getPageNum(),searchData.getPageSize());
        searchData.getData().setJobStatus(JobStatus.COMPLIANCE);
        searchData.getData().setJobVisibility(JobVisibility.PUBLIC);
        jobMapper.selectJobMessage(jobIPage, searchData.getData());
        return SearchData.pageData((int) jobIPage.getCurrent(),
                (int) jobIPage.getSize(),
                (int) jobIPage.getTotal(),
                jobIPage.getRecords());
    }

    /**
     * TODO 添加招聘信息
     */
    @Override
    public boolean addJobMessage(Job job,String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        job.setUserId(userId);
        job.setCreatedAt(new Date());
        return jobMapper.insert(job) > 0;
    }

    /**
     * TODO 获取jobId单个招聘信息
     */
    @Override
    public Job getOneJobMessage(Integer jobId,JobStatus jobStatus,JobVisibility jobVisibility) {
        return jobMapper.selectOneByJobId(jobId,jobStatus,jobVisibility);
    }
}
