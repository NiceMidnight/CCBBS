package cc.backend.manager.service;

import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.TopicForPost;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
public interface TopicForPostService {
    SearchData<TopicForPost> getTFP( SearchData<TopicForPost> queryCondition);
    boolean enableTFP(Integer topicId,String tokenInfo);
    boolean disableTFP(Integer topicId,String tokenInfo);
}
