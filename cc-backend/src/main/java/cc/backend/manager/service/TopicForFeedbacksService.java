package cc.backend.manager.service;

import cc.backend.entity.feedback.TopicForFeedback;
import cc.backend.enums.TopicStatus;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
public interface TopicForFeedbacksService {
    List<TopicForFeedback> getTFFOptions();

    List<TopicForFeedback> getTFF(TopicStatus topicStatus);

    boolean changeTopicStatus(Integer topicId,TopicStatus topicStatus);

    boolean updateTopicColor(Integer topicId,String topicColor);

    boolean addTopic(TopicForFeedback topicForFeedback);
}
