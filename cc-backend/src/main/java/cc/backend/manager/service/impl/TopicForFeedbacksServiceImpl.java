package cc.backend.manager.service.impl;

import cc.backend.entity.feedback.TopicForFeedback;
import cc.backend.enums.TopicStatus;
import cc.backend.manager.mapper.TopicForFeedbacksMapper;
import cc.backend.manager.service.TopicForFeedbacksService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
@Service
public class TopicForFeedbacksServiceImpl implements TopicForFeedbacksService {
    @Autowired
    private TopicForFeedbacksMapper topicForFeedbacksMapper;

    @Override
    public List<TopicForFeedback> getTFFOptions() {
        return topicForFeedbacksMapper.selectList(null);
    }

    @Override
    public List<TopicForFeedback> getTFF(TopicStatus topicStatus) {
        List<TopicForFeedback> topicForFeedbacks = topicForFeedbacksMapper.selectTFF(topicStatus);
        return topicForFeedbacks;
    }

    @Override
    public boolean changeTopicStatus(Integer topicId, TopicStatus topicStatus) {
        return topicForFeedbacksMapper.updateTopicStatus(topicId,topicStatus) > 0;
    }

    @Override
    public boolean updateTopicColor(Integer topicId, String topicColor) {
        return topicForFeedbacksMapper.updateTopicColor(topicId,topicColor) > 0;
    }
}
