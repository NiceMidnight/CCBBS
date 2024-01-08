package cc.backend.user.service.impl;

import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.enums.PostStatus;
import cc.backend.enums.PostVisibility;
import cc.backend.user.mapper.PostMapper;
import cc.backend.user.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/17
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    /**
     * TODO 获取所有帖子
     */
    @Override
    public SearchData<Post> getAllPost(SearchData<Post> queryCondition,String postMsg) {
        IPage<Post> iPageForPost = new Page<>(queryCondition.getPageNum(), queryCondition.getPageSize());
        postMapper.selectAllPost(iPageForPost, queryCondition.getData().getTopicId(),postMsg ,PostVisibility.PUBLIC,PostStatus.COMPLIANCE);
        return SearchData.pageData(
                (int) iPageForPost.getCurrent(),
                (int) iPageForPost.getSize(),
                (int) iPageForPost.getTotal(),
                iPageForPost.getRecords());
    }

    /**
     * TODO 通过主题获取帖子
     */
    @Override
    public SearchData<Post> getPostByTopic(SearchData<Post> queryCondition) {
        IPage<Post> iPageForPost = new Page<>(queryCondition.getPageNum(), queryCondition.getPageSize());
        queryCondition.getData().setPostStatus(PostStatus.COMPLIANCE);
        queryCondition.getData().setPostVisibility(PostVisibility.PUBLIC);
        postMapper.getAllPostByTopicId(iPageForPost,queryCondition.getData());
        return null;
    }

}
