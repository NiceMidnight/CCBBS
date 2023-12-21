package cc.backend.manager.service.impl;

import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.enums.PostStatus;
import cc.backend.manager.mapper.PostsMapper;
import cc.backend.manager.service.PostsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/24
 */
@Service
public class PostsServiceImpl implements PostsService {
    @Autowired
    private PostsMapper postsMapper;
    /**
     * TODO 查询所有帖子数据
     */
    @Override
    public SearchData<Post> getAllPosts(SearchData<Post> searchData) {
        IPage<Post> iPage = new Page<>(searchData.getPageNum(), searchData.getPageSize());
        postsMapper.selectAllPost(iPage,
                searchData.getData().getUserName(),
                searchData.getData().getPostTitle(),
                searchData.getData().getPostContent());
        return SearchData.pageData(
                (int) iPage.getCurrent(),
                (int) iPage.getSize(),
                (int) iPage.getTotal(),
                iPage.getRecords());
    }

    /**
     * TODO 启用
     */
    @Override
    public boolean compliancePost(Integer postId) {
        return postsMapper.updatePostStatus(postId, PostStatus.COMPLIANCE) > 0;
    }

    /**
     * TODO 禁用
     */
    @Override
    public boolean irregularityPost(Integer postId) {
        return postsMapper.updatePostStatus(postId,PostStatus.IRREGULARITY) > 0;
    }

    /**
     * TODO 查看某个帖子
     */
    @Override
    public Post postView(Integer postId) {
        Post post = postsMapper.selectById(postId);
        return post;
    }

}
