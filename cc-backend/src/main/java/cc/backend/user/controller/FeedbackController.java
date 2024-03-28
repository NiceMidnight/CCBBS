package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.common.token.Token;
import cc.backend.entity.feedback.Feedback;
import cc.backend.user.service.impl.FeedbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/23
 */
@RestController
@RequestMapping("/user/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackServiceImpl feedbackService;
    @Autowired
    private Token token;
    @GetMapping("/getFeedback")
    public Result getFeedback(@RequestHeader("Authorization")String tokenInfo)
    {
        int userId = token.getUserId(tokenInfo);
        List<Feedback> feedback = feedbackService.getFeedback(userId);
        return Result.successCDM(feedback,"获取反馈信息成功");
    }

    @GetMapping("/remind")
    public Result remind(@RequestParam("feedbackId")Integer feedbackId,@RequestHeader("Authorization")String tokenInfo)
    {
        int userId = token.getUserId(tokenInfo);
        boolean isRemind = feedbackService.remind(feedbackId, userId);
        if (isRemind)
        {
            return Result.successCM("催促成功");
        }
        return Result.error("催促失败");
    }

    @RequestMapping("/submitFeedback")
    public Result submitFeedback(@RequestBody Feedback feedback,@RequestHeader("Authorization")String tokenInfo)
    {
        int userId = token.getUserId(tokenInfo);
        boolean isSubmit = feedbackService.submitFeedback(feedback, userId);
        if (isSubmit)
        {
            return Result.successCM("提交反馈成功");
        }
        return Result.error("提交反馈失败");
    }
}
