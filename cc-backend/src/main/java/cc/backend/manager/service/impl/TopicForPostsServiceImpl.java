package cc.backend.manager.service.impl;

import cc.backend.common.Token;
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
     * TODO 获取帖子主题内容
     */
    @Override
    public SearchData<TopicForPost> getTFP(SearchData<TopicForPost> queryCondition) {
        IPage<TopicForPost> iPage = new Page<>(queryCondition.getPageNum(), queryCondition.getPageSize());
        topicForPostsMapper.selectTFP(iPage,queryCondition.getData().getTopicName());
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * TODO 添加主题
     */
    @Override
    public boolean addTFP(TopicForPost topicForPost,String tokenInfo) {
        topicForPost.setCreatedId((long) token.getUserId(tokenInfo));
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

}
