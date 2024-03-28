package cc.backend.user.service.impl;

import cc.backend.entity.feedback.TopicForFeedback;
import cc.backend.user.mapper.TopicForFeedbackMapper;
import cc.backend.user.service.TopicForFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/23
 */
@Service
public class TopicForFeedbackServiceImpl implements TopicForFeedbackService {
    @Autowired
    private TopicForFeedbackMapper topicForFeedbackMapper;
    @Override
    public List<TopicForFeedback> getTFFOptions() {
        return topicForFeedbackMapper.selectList(null);
    }
}
