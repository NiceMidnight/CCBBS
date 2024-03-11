package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;
import cc.backend.enums.JobVisibility;
import cc.backend.manager.service.impl.JobsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 招聘信息
 * @Author Tiamo_null
 * @Date 2024/3/11
 */
@RestController
@RequestMapping("/manager/job")
public class JobsController {
    @Autowired
    private JobsServiceImpl jobsService;
    @RequestMapping("/getJobMessage")
    public Result getJobMessage(@RequestBody SearchData<Job> searchData,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)

    {
        SearchData<Job> jobMessage = jobsService.getJobMessage(searchData, startTime, endTime);
        return Result.successCDM(jobMessage,"获取招聘信息成功");
    }

    @GetMapping("/getJobStatus")
    public Result getJobStatus()
    {
        List<JobStatus> jobStatusList = Arrays.asList(JobStatus.values());
        return Result.successCDM(jobStatusList,"获取招聘信息合法状态枚举类成功");
    }

    @GetMapping("/getJobVisibility")
    public Result getJobVisibility()
    {
        List<JobVisibility> jobVisibilityList = Arrays.asList(JobVisibility.values());
        return Result.successCDM(jobVisibilityList,"获取招聘信息可见性枚举类成功");
    }

    /**
     * TODO 操作帖子状态
     */
    @GetMapping("/complianceJob")
    public Result complianceJob(@RequestParam("jobId")Integer jobId)
    {
        boolean isCompliance = jobsService.updateJobStatus(jobId, JobStatus.COMPLIANCE);
        if (isCompliance)
        {
            return Result.successCM("对招聘"+jobId+"（id）合规操作成功");
        }
        return Result.error("对招聘"+jobId+"（id）合规操作失败");
    }

    @GetMapping("/irregularityJob")
    public Result irregularityJob(@RequestParam("jobId")Integer jobId)
    {
        boolean isIrregularity = jobsService.updateJobStatus(jobId, JobStatus.IRREGULARITY);
        if (isIrregularity)
        {
            return Result.successCM("对招聘"+jobId+"（id）违规操作成功");
        }
        return Result.error("对招聘"+jobId+"（id）违规操作失败");
    }

    @GetMapping("/jobView")
    public Result jobView(@RequestParam("jobId")Integer jobId)
    {
        Job job = jobsService.jobView(jobId);
        return Result.successCDM(job,"获取单个招聘信息成功");
    }
}
