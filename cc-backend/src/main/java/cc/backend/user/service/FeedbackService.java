package cc.backend.user.service;


import cc.backend.entity.feedback.Feedback;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/23
 */
public interface FeedbackService {
    List<Feedback> getFeedback(int userId);
    boolean remind(Integer feedbackId,Integer userId);
    boolean submitFeedback(Feedback feedback,Integer userId);
}
