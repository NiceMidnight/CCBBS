package cc.backend.manager.service.impl;

import cc.backend.entity.Article;
import cc.backend.entity.Dict;
import cc.backend.entity.Post;
import cc.backend.entity.User;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.manager.mapper.ArticlesMapper;
import cc.backend.manager.mapper.JobsMapper;
import cc.backend.manager.mapper.PostsMapper;
import cc.backend.manager.mapper.UsersMapper;
import cc.backend.manager.service.EChartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/16
 */
@Service
public class EChartServiceImpl implements EChartService {
    @Autowired
    private PostsMapper postsMapper;
    /**
     * TODO 获取对应主题的帖子数量
     */
    @Override
    public Long getCountOfPostForTFP(int topicId) {
        QueryWrapper<Post> postQueryWrapper = new QueryWrapper<>();
        postQueryWrapper.eq("topic_id",topicId);
        return postsMapper.selectCount(postQueryWrapper);
    }

    @Autowired
    private UsersMapper usersMapper;
    /**
     * TODO 获取对应字典的用户数量
     */
    @Override
    public Long getCountOfUserForDict(int dictTypeId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_role",dictTypeId);
        return usersMapper.selectCount(userQueryWrapper);
    }


    @Autowired
    private JobsMapper jobsMapper;
    /**
     * TODO 获取对应主题的招聘信息数量
     */
    @Override
    public Long getCountOfJobForTFJ(int topicId) {
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("topic_id",topicId);
        return jobsMapper.selectCount(jobQueryWrapper);
    }

    @Autowired
    private ArticlesMapper articlesMapper;
    /**
     * TODO 获取对应主题的公告数量
     */
    @Override
    public Long getCountOfArticleForTFA(int topicId) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("topic_id",topicId);
        return articlesMapper.selectCount(articleQueryWrapper);
    }
}
