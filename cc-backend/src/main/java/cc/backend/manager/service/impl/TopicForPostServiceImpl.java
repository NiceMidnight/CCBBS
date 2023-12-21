package cc.backend.manager.service.impl;

import cc.backend.commom.Token;
import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.TopicForPost;
import cc.backend.enums.TopicStatus;
import cc.backend.manager.mapper.TopicForPostMapper;
import cc.backend.manager.service.TopicForPostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
@Service
public class TopicForPostServiceImpl implements TopicForPostService {

    @Autowired
    private TopicForPostMapper topicForPostMapper;
    @Autowired
    private Token token;
    /**
     * TODO 获取帖子主题内容
     */
    @Override
    public SearchData<TopicForPost> getTFP(SearchData<TopicForPost> queryCondition) {
        IPage<TopicForPost> iPage = new Page<>(queryCondition.getPageNum(), queryCondition.getPageSize());
        topicForPostMapper.selectTFP(iPage,queryCondition.getData().getTopicName());
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * TODO 启用主题
     */
    @Override
    public boolean enableTFP(Integer topicId,String tokenInfo) {
        Long updatedId = (long) token.getUserId(tokenInfo);
        return topicForPostMapper.updateTFPStatus(topicId, TopicStatus.ENABLE,updatedId,new Date()) > 0;
    }

    /**
     * TODO 禁用主题
     */
    @Override
    public boolean disableTFP(Integer topicId,String tokenInfo) {
        Long updatedId = (long) token.getUserId(tokenInfo);
        return topicForPostMapper.updateTFPStatus(topicId, TopicStatus.DISABLE,updatedId,new Date()) > 0;
    }

}
