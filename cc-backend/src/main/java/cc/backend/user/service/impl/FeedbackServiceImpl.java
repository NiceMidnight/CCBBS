package cc.backend.user.service.impl;


import cc.backend.entity.feedback.Feedback;
import cc.backend.enums.FeedbackReminderStatus;
import cc.backend.user.mapper.FeedbackMapper;
import cc.backend.user.service.FeedbackService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/23
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public List<Feedback> getFeedback(int userId) {
        return feedbackMapper.selectAllByUserId(userId);
    }

    @Override
    public boolean remind(Integer feedbackId, Integer userId) {
        Feedback feedback = feedbackMapper.selectById(feedbackId);
        if (Objects.equals(userId, feedback.getCreatorId()))
        {
            feedback.setReminderStatus(FeedbackReminderStatus.Reminder);
            UpdateWrapper<Feedback> feedbackUpdateWrapper = new UpdateWrapper<>();
            feedbackUpdateWrapper.eq("feedback_id",feedbackId);
            return feedbackMapper.update(feedback,feedbackUpdateWrapper) > 0;
        }
        return false;
    }

    @Override
    public boolean submitFeedback(Feedback feedback, Integer userId) {
        feedback.setCreatorId(userId);
        feedback.setCreatedTime(new Date());
        return feedbackMapper.insert(feedback) > 0;
    }
}
