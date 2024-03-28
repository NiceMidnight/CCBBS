package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.feedback.TopicForFeedback;
import cc.backend.user.service.impl.FeedbackServiceImpl;
import cc.backend.user.service.impl.TopicForFeedbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/23
 */
@RestController
@RequestMapping("/user/tff")
public class TopicForFeedbackController {
    @Autowired
    private TopicForFeedbackServiceImpl topicForFeedbackService;
    @GetMapping("/getTFFOptions")
    public Result getTFFOptions()
    {
        List<TopicForFeedback> tffOptions = topicForFeedbackService.getTFFOptions();
        return Result.successCDM(tffOptions,"反馈类型选择器获取成功");
    }

}
