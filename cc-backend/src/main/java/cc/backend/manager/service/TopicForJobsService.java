package cc.backend.manager.service;

import cc.backend.entity.forrecruiter.TopicForJob;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/11
 */
public interface TopicForJobsService {
    List<TopicForJob> getTopicForJob();
    boolean updateTopicColor(TopicForJob topicForJob);
}
