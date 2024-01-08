package cc.backend.user.service.impl;

import cc.backend.entity.forpost.TopicForPost;
import cc.backend.enums.TopicStatus;
import cc.backend.user.mapper.TopicForPostMapper;
import cc.backend.user.service.TopicForPostService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 帖子主题
 * @Author Tiamo_null
 * @Date 2023/12/22
 */
@Service
public class TopicForPostServiceImpl implements TopicForPostService {

    @Autowired
    private TopicForPostMapper topicForPostMapper;
    @Override
    public List<TopicForPost> getPostTopic() {
        LambdaQueryWrapper<TopicForPost> topicForPostLambdaQueryWrapper = new LambdaQueryWrapper<>();
        topicForPostLambdaQueryWrapper.eq(TopicForPost::getTopicStatus, TopicStatus.ENABLE)
                .select(TopicForPost::getTopicId,TopicForPost::getTopicName,TopicForPost::getCreatedTime,TopicForPost::getUpdatedTime);
        return topicForPostMapper.selectList(topicForPostLambdaQueryWrapper);
    }
}
