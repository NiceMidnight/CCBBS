package cc.backend.user.service.impl;

import cc.backend.entity.TopicForArticle;
import cc.backend.entity.forrecruiter.TopicForJob;
import cc.backend.user.mapper.TopicForArticleMapper;
import cc.backend.user.mapper.TopicForJobMapper;
import cc.backend.user.service.TopicForArticleService;
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
public class TopicForArticleServiceImpl implements TopicForArticleService {

    @Autowired
    private TopicForArticleMapper topicForArticleMapper;
    /**
     * TODO 获取公告信息类型
     */
    @Override
    public List<TopicForArticle> getTopicForArticle() {
        return topicForArticleMapper.selectList(null);
    }
}
