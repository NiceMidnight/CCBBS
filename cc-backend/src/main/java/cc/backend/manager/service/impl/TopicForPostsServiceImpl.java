package cc.backend.manager.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.TopicForPost;
import cc.backend.enums.TopicStatus;
import cc.backend.manager.mapper.TopicForPostsMapper;
import cc.backend.manager.service.TopicForPostsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description 帖子主题
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
@Service
public class TopicForPostsServiceImpl implements TopicForPostsService {

    @Autowired
    private TopicForPostsMapper topicForPostsMapper;
    @Autowired
    private Token token;
    /**
     * @description TODO 获取帖子主题内容
     * @param queryCondition
     * @return: cc.backend.entity.SearchData<cc.backend.entity.forpost.TopicForPost>
     */
    @Override
    public SearchData<TopicForPost> getTFP(SearchData<TopicForPost> queryCondition) {
        IPage<TopicForPost> iPage = new Page<>(queryCondition.getPageNum(), queryCondition.getPageSize());
        topicForPostsMapper.selectTFP(iPage,queryCondition.getData().getTopicName());
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * @description TODO 添加主题
     * @param topicForPost
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean addTFP(TopicForPost topicForPost,String tokenInfo) {
        topicForPost.setCreatedId(token.getUserId(tokenInfo));
        topicForPost.setCreatedTime(new Date());
        return topicForPostsMapper.insert(topicForPost) > 0;
    }

    /**
     * TODO 启用主题
     */
    @Override
    public boolean enableTFP(Integer topicId,String tokenInfo) {
        Long updatedId = (long) token.getUserId(tokenInfo);
        return topicForPostsMapper.updateTFPStatus(topicId, TopicStatus.ENABLE,updatedId,new Date()) > 0;
    }

    /**
     * TODO 禁用主题
     */
    @Override
    public boolean disableTFP(Integer topicId,String tokenInfo) {
        Long updatedId = (long) token.getUserId(tokenInfo);
        return topicForPostsMapper.updateTFPStatus(topicId, TopicStatus.DISABLE,updatedId,new Date()) > 0;
    }

    /**
     * @description TODO 通过id获取主题内容
     * @param topicId
     * @return: cc.backend.entity.forpost.TopicForPost
     */
    @Override
    public TopicForPost getTopicById(Integer topicId) {
        return topicForPostsMapper.selectById(topicId);
    }

    /**
     * @description TODO 编辑主题信息
     * @param topicName
     * @param topicId
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean editTopic(String topicName, Integer topicId, String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        TopicForPost topicForPost = new TopicForPost();
        topicForPost.setTopicId(topicId);
        topicForPost.setTopicName(topicName);
        topicForPost.setUpdatedId(userId);
        topicForPost.setUpdatedTime(new Date());
        return topicForPostsMapper.updateTopicNameById(topicForPost) > 0;
    }

    @Override
    public boolean updateTopicColor(int topicId, String topicColor) {
        return topicForPostsMapper.updateTopicColor(topicId,topicColor) > 0;
    }

    /**
     * TODO 获取所有TFP数据
     */
    @Override
    public List<TopicForPost> getTFP() {
        return topicForPostsMapper.selectList(null);
    }

}
