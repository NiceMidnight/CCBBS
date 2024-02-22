package cc.backend.manager.service.impl;

import cc.backend.entity.TopicForArticle;
import cc.backend.manager.mapper.TopicForArticlesMapper;
import cc.backend.manager.service.TopicForArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
@Service
public class TopicForArticlesServiceImpl implements TopicForArticlesService {
    @Autowired
    private TopicForArticlesMapper topicForArticlesMapper;

    @Override
    public List<TopicForArticle> getArticleTopic() {
        return topicForArticlesMapper.selectList(null);
    }
}
