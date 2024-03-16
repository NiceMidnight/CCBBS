package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.Feedback;
import cc.backend.entity.SearchData;
import cc.backend.manager.service.impl.FeedbacksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
        return Result.successCDM(allFeedback,"获取所有用户信息");
    }
}
