package cc.backend.manager.service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/16
 */
public interface EChartService {
    Long getCountOfPostForTFP(int topicId);
    Long getCountOfUserForDict(int dictTypeId);
    Long getCountOfJobForTFJ(int topicId);
    Long getCountOfArticleForTFA(int topicId);
}
