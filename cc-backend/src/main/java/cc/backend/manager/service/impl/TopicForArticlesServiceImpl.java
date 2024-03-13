package cc.backend.manager.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.TopicForArticle;
import cc.backend.enums.TopicStatus;
import cc.backend.manager.mapper.TopicForArticlesMapper;
import cc.backend.manager.service.TopicForArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private Token token;

    @Override
    public List<TopicForArticle> getArticleTopic() {
        return topicForArticlesMapper.selectTopicForArticle();
    }

    @Override
    public boolean updateTopicForArticleStatus(Integer topicId, TopicStatus topicStatus) {
        return topicForArticlesMapper.updateTopicStatus(topicId,topicStatus) > 0;
    }

    @Override
    public boolean updateTopicColor(Integer topicId, String topicColor) {
        return topicForArticlesMapper.updateTopicColor(topicId,topicColor) > 0;
    }

    @Override
    public boolean addTopic(TopicForArticle topicForArticle, String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        topicForArticle.setCreatedId(userId);
        topicForArticle.setCreatedTime(new Date());
        return topicForArticlesMapper.insert(topicForArticle) > 0;
    }

}
