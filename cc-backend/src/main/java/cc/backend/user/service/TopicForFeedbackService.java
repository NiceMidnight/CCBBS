package cc.backend.user.service;

import cc.backend.entity.feedback.TopicForFeedback;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/23
 */
public interface TopicForFeedbackService {
    List<TopicForFeedback> getTFFOptions();
}
