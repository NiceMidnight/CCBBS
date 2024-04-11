package cc.backend.user.service;

import cc.backend.entity.TopicForArticle;
import cc.backend.entity.forrecruiter.TopicForJob;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
public interface TopicForArticleService {
    List<TopicForArticle> getTopicForArticle();
}
