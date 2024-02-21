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

import java.time.LocalDateTime;

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
     * @description TODO 查询所有帖子数据
     * @param searchData
     * @param startTime
     * @param endTime
     * @return: cc.backend.entity.SearchData<cc.backend.entity.Post>
     */
    @Override
    public SearchData<Post> getAllPosts(SearchData<Post> searchData, LocalDateTime startTime, LocalDateTime endTime)
    {
        IPage<Post> iPage = new Page<>(searchData.getPageNum(), searchData.getPageSize());
        postsMapper.selectAllPost(iPage, searchData.getData(),startTime,endTime);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(), iPage.getRecords());
    }

    /**
     * @description TODO 启用
     * @param postId
     * @return: boolean
     */
    @Override
    public boolean compliancePost(Integer postId) {
        return postsMapper.updatePostStatus(postId, PostStatus.COMPLIANCE) > 0;
    }

    /**
     * @description TODO 禁用
     * @param postId
     * @return: boolean
     */
    @Override
    public boolean irregularityPost(Integer postId) {
        return postsMapper.updatePostStatus(postId,PostStatus.IRREGULARITY) > 0;
    }

    /**
     * @description TODO 查看某个帖子
     * @param postId
     * @return: cc.backend.entity.Post
     */
    @Override
    public Post postView(Integer postId) {
        return postsMapper.selectById(postId);
    }

    /**
     * @description TODO 获取帖子标题
     * @param postId
     * @return: java.lang.String
     */
    @Override
    public String getPostTitle(Integer postId) {
        return postsMapper.selectPostTitleById(postId);
    }

}
