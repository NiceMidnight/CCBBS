package cc.backend.manager.service;

import cc.backend.entity.TopicForArticle;
import cc.backend.enums.TopicStatus;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
public interface TopicForArticlesService {
    List<TopicForArticle> getArticleTopic();
    boolean updateTopicForArticleStatus(Integer topicId, TopicStatus topicStatus);
    boolean updateTopicColor(Integer topicId,String topicColor);
    boolean addTopic(TopicForArticle topicForArticle,String tokenInfo);
}
