package cc.backend.user.service.impl;

import cc.backend.entity.forrecruiter.TopicForJob;
import cc.backend.user.mapper.TopicForJobMapper;
import cc.backend.user.service.TopicForJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@Service
public class TopicForJobServiceImpl implements TopicForJobService {

    @Autowired
    private TopicForJobMapper topicForJobMapper;
    /**
     * TODO 获取招聘信息主题
     */
    @Override
    public List<TopicForJob> getTopicForJob() {
        return topicForJobMapper.selectList(null);
    }
}
