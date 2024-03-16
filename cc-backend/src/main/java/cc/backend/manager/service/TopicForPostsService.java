package cc.backend.manager.service;

import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.TopicForPost;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
public interface TopicForPostsService {
    SearchData<TopicForPost> getTFP( SearchData<TopicForPost> queryCondition);
    boolean addTFP(TopicForPost topicForPost,String tokenInfo);
    boolean enableTFP(Integer topicId,String tokenInfo);
    boolean disableTFP(Integer topicId,String tokenInfo);
    TopicForPost getTopicById(Integer topicId);
    boolean editTopic(String topicName,Integer topicId,String tokenInfo);
    boolean updateTopicColor(int topicId,String topicColor);
    List<TopicForPost> getTFP();
}
