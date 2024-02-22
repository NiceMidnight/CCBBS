package cc.backend.manager.service;

import cc.backend.entity.TopicForArticle;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
public interface TopicForArticlesService {
    List<TopicForArticle> getArticleTopic();
}
