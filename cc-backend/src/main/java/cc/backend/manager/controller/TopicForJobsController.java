package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.forrecruiter.TopicForJob;
import cc.backend.manager.service.impl.TopicForJobsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/11
 */
@RestController
@RequestMapping("/manager/tfj")
public class TopicForJobsController {
    @Autowired
    private TopicForJobsServiceImpl topicForJobsService;
    @GetMapping("/getTopicForJob")
    public Result getTopicForJobs()
    {
        List<TopicForJob> topicForJob = topicForJobsService.getTopicForJob();
        return Result.successCDM(topicForJob,"获取招聘主题成功");
    }
    /**
     * TODO 更新招聘主题颜色
     */
    @PostMapping("/updateTopicColor")
    public Result updateTopicColor(@RequestBody TopicForJob topicForJob)
    {
        boolean isUpdateTopicColor = topicForJobsService.updateTopicColor(topicForJob);
        if (isUpdateTopicColor)
        {
            return Result.successCM("更改招聘主题颜色成功(id="+topicForJob.getTopicId()+")");
        }
        return Result.error("更改招聘主题颜色失败(id="+topicForJob.getTopicId()+")");
    }

}
