package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.common.token.Token;
import cc.backend.entity.feedback.TopicForFeedback;
import cc.backend.enums.TopicStatus;
import cc.backend.manager.service.impl.TopicForFeedbacksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
@RestController
@RequestMapping("/manager/tff")
public class TopicForFeedbacksController {
    @Autowired
    private TopicForFeedbacksServiceImpl topicForFeedbacksService;
    @Autowired
    private Token token;
    @GetMapping("/getTFFOptions")
    public Result getTFFOptions()
    {
        List<TopicForFeedback> tffOptions = topicForFeedbacksService.getTFFOptions();
        return Result.successCDM(tffOptions,"获取主题");
    }

    @GetMapping("/getTopicStatus")
    public Result getTopicStatus()
    {
        List<TopicStatus> topicStatusList = Arrays.asList(TopicStatus.values());
        return Result.successCDM(topicStatusList,"获取主题状态成功");
    }

    @GetMapping("/getTopicForFeedback")
    public Result getTopicForFeedback(@RequestParam(required = false) TopicStatus topicStatus)
    {
        System.out.println(topicStatus);
        List<TopicForFeedback> tffList = topicForFeedbacksService.getTFF(topicStatus);
        return Result.successCDM(tffList,"获取反馈类型成功");
    }

    @GetMapping("/enableTopic")
    public Result enableTopic(@RequestParam("topicId") Integer topicId)
    {
        boolean isChangeStatus = topicForFeedbacksService.changeTopicStatus(topicId, TopicStatus.ENABLE);
        if (isChangeStatus)
        {
            return Result.successCM("启用类型成功(id:"+topicId+")");
        }
        return Result.error("启用类型失败(id:"+topicId+")");
    }

    @GetMapping("/disableTopic")
    public Result disableTopic(@RequestParam("topicId") Integer topicId)
    {
        boolean isChangeStatus = topicForFeedbacksService.changeTopicStatus(topicId, TopicStatus.DISABLE);
        if (isChangeStatus)
        {
            return Result.successCM("禁用类型成功(id:"+topicId+")");
        }
        return Result.error("禁用类型失败(id:"+topicId+")");
    }

    @GetMapping("/updateTFFColor")
    public Result updateTFFColor(@RequestParam("topicId") Integer topicId,
                                 @RequestParam("topicColor")String topicColor)
    {
        boolean isUpdateTopicColor = topicForFeedbacksService.updateTopicColor(topicId, topicColor);
        if (isUpdateTopicColor)
        {
            return Result.successCM("更新颜色成功");
        }
        return Result.error("更新颜色失败");
    }

    @PostMapping("/addTFF")
    public Result addTFF(@RequestBody TopicForFeedback topicForFeedback,@RequestHeader("Authorization")String tokenInfo)
    {

        int userId = token.getUserId(tokenInfo);
        topicForFeedback.setUserId(userId);
        boolean isAddTFF = topicForFeedbacksService.addTopic(topicForFeedback);
        if (isAddTFF)
        {
            return Result.successCM("添加反馈类型成功");
        }
        return Result.error("添加反馈类型失败");
    }
}
