package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.feedback.Feedback;
import cc.backend.entity.SearchData;
import cc.backend.enums.FeedbackReminderStatus;
import cc.backend.enums.FeedbackStatus;
import cc.backend.manager.service.impl.FeedbacksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/15
 */
@RestController
@RequestMapping("/manager/feedback")
public class FeedbacksController {
    @Autowired
    private FeedbacksServiceImpl feedbacksService;

    @RequestMapping("/getAllFeedback")
    public Result getAllFeedback(@RequestBody SearchData<Feedback> searchData,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime){
        SearchData<Feedback> allFeedback = feedbacksService.getAllFeedback(searchData, startTime, endTime);
        return Result.successCDM(allFeedback,"获取反馈信息成功");
    }

    @GetMapping("/getFeedbackStatusOption")
    public Result getFeedbackStatusOption()
    {
        List<FeedbackStatus> feedbackStatusList = Arrays.asList(FeedbackStatus.values());
        return Result.successCDM(feedbackStatusList,"获取帖子处理状态选择器成功");
    }

    @GetMapping("/getReminderStatusOption")
    public Result getReminderStatusOption()
    {
        List<FeedbackReminderStatus> feedbackReminderStatuses = Arrays.asList(FeedbackReminderStatus.values());
        return Result.successCDM(feedbackReminderStatuses,"获取帖子催促状态选择器成功");
    }

    @GetMapping("/upProgress")
    public Result upProgress(@RequestParam("feedbackId")Integer feedbackId,@RequestParam("feedbackStatus")String feedbackStatus)
    {
        boolean isUpdate = feedbacksService.updateFeedbackStatus(feedbackId, feedbackStatus, true);
        if (isUpdate)
        {
            return Result.successCM("更新进度成功");
        }
        return Result.error("更新进度失败");
    }

    @GetMapping("/downProgress")
    public Result downProgress(@RequestParam("feedbackId")Integer feedbackId,@RequestParam("feedbackStatus")String feedbackStatus)
    {
        boolean isUpdate = feedbacksService.updateFeedbackStatus(feedbackId, feedbackStatus, false);
        if (isUpdate)
        {
            return Result.successCM("更新进度成功");
        }
        return Result.error("更新进度失败");
    }
}
