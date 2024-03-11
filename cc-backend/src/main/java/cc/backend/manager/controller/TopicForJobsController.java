package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.forrecruiter.TopicForJob;
import cc.backend.manager.service.impl.TopicForJobsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
