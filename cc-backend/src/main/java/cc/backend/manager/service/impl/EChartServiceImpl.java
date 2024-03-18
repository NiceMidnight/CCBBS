package cc.backend.manager.service.impl;

import cc.backend.entity.Article;
import cc.backend.entity.Dict;
import cc.backend.entity.Post;
import cc.backend.entity.User;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.manager.mapper.*;
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
    @Override
    public Long getCountOfArticleForTFA(int topicId) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("topic_id",topicId);
        return articlesMapper.selectCount(articleQueryWrapper);
    }

    @Autowired
    private LogsMapper logsMapper;
    /**
     * TODO 获取当日活跃用户数量---获取昨日活跃用户数量
     */
    @Override
    public Long getCountOfTodayDailyUniqueVisitorsLog() {
        return logsMapper.countByToday();
    }
    @Override
    public Long getCountOfYesterdayDailyUniqueVisitorsLog() {
        return logsMapper.countByYesterday();
    }
    /**
     * TODO 获取当月活跃用户数量---获取上个月活跃用户数量
     */
    @Override
    public Long getCountOfCurrentMonthDailyUniqueVisitorsLog() {
        return logsMapper.countByCurrentMonth();
    }
    @Override
    public Long getCountOfLastMonthDailyUniqueVisitorsLog() {
        return logsMapper.countByLastMonth();
    }


    /**
     * TODO 获取当月公告数量---获取上月公告数量
     */
    @Override
    public Long getCountOfCurrentMonthArticle() {
        return articlesMapper.countByCurrentMonth();
    }
    @Override
    public Long getCountOfLathMonthArticle() {
        return articlesMapper.countByLastMonth();
    }

    /**
     * TODO 获取当周帖子数量---获取上周帖子数量
     */
    @Override
    public Long getCountOfCurrentWeekPost() {
        return postsMapper.countByCurrentWeek();
    }
    @Override
    public Long getCountOfLastWeekPost() {
        return postsMapper.countByLastWeek();
    }

    /**
     * TODO 获取当月招聘信息数量---获取上月招聘信息数量
     */
    @Override
    public Long getCountOfCurrentMonthJob() {
        return jobsMapper.countByCurrentMonth();
    }
    @Override
    public Long getCountOfLastMonthJob() {
        return jobsMapper.countByLastMonth();
    }

    /**
     * TODO 获取当周反馈数量---获取上周反馈数量
     */
    @Autowired
    private FeedbacksMapper feedbacksMapper;
    @Override
    public Long getCountOfCurrentWeekFeedback() {
        return feedbacksMapper.countByCurrentWeek();
    }
    @Override
    public Long getCountOfLastWeekFeedback() {
        return feedbacksMapper.countByLastWeek();
    }

}
