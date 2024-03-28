package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.JobStatus;
import cc.backend.enums.JobVisibility;
import cc.backend.user.service.impl.JobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@RestController
@RequestMapping("/user/job")
public class JobController {
    @Autowired
    private JobServiceImpl jobService;
    /**
     * TODO 获取招聘信息
     */
    @PostMapping("/getJobMessage")
    public Result getJobMessage(@RequestBody SearchData<Job> searchData)
    {
        SearchData<Job> jobMessage = jobService.getJobMessage(searchData);
        return Result.successCDT(jobMessage,"获取招聘信息成功");
    }
    /**
     * TODO 添加招聘信息
     */
    @RequestMapping("/addJobMessage")
    public Result addJobMessage(@RequestBody Job job, @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isAdd = jobService.addJobMessage(job, tokenInfo);
        if (isAdd)
        {
            return Result.successCM("提交招聘信息成功，将在三个工作日内通过");
        }
        return Result.error("提交招聘信息失败");
    }
    /**
     * TODO 获取单个招聘信息
     */
    @GetMapping("/getOneJobMessage")
    public Result getOneJobMessage(@RequestParam("jobId")Integer jobId)
    {
        jobService.addViewCount(jobId);
        Job oneJobMessage = jobService.getOneJobMessage(jobId, JobStatus.COMPLIANCE, JobVisibility.PUBLIC);
        return Result.successCDM(oneJobMessage,"获取id为"+jobId+"的招聘信息成功");
    }
}
