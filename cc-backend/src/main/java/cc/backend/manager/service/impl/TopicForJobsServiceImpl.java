package cc.backend.manager.service.impl;

import cc.backend.entity.forrecruiter.TopicForJob;
import cc.backend.manager.mapper.TopicForJobsMapper;
import cc.backend.manager.service.TopicForJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/11
 */
@Service
public class TopicForJobsServiceImpl implements TopicForJobsService {

    @Autowired
    private TopicForJobsMapper topicForJobsMapper;
    @Override
    public List<TopicForJob> getTopicForJob() {
        return topicForJobsMapper.selectList(null);
    }

    @Override
    public boolean updateTopicColor(TopicForJob topicForJob) {
        return topicForJobsMapper.updateById(topicForJob) > 0;
    }
}
