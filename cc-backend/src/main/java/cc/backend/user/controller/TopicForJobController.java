package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.forrecruiter.TopicForJob;
import cc.backend.user.service.impl.TopicForJobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@RestController
@RequestMapping("/user/tfj")
public class TopicForJobController {
    @Autowired
    private TopicForJobServiceImpl topicForJobService;
    /**
     * TODO 获取招聘信息主题
     */
    @GetMapping("/getTopicForJob")
    public Result getTopicForJob()
    {
        List<TopicForJob> topicForJob = topicForJobService.getTopicForJob();
        return Result.successCDM(topicForJob,"获取招聘信息主题成功");
    }
}
