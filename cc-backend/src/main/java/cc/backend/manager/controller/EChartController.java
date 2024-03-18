package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.Dict;
import cc.backend.entity.TopicForArticle;
import cc.backend.entity.forpost.TopicForPost;
import cc.backend.entity.forrecruiter.TopicForJob;
import cc.backend.manager.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description echarts图表数据
 * @Author Tiamo_null
 * @Date 2024/3/16
 */
@RestController
@RequestMapping("/manager/echarts")
public class EChartController {
    @Autowired
    private EChartServiceImpl eChartService;

    @Autowired
    private TopicForPostsServiceImpl topicForPostsService;
    /**
     * TODO 获取帖子主题图表数据
     */
    @GetMapping("/getTFPEChartData")
    public Result getTFPEChartData()
    {
        List<TopicForPost> tfp = topicForPostsService.getTFP();
        for (TopicForPost topicForPost : tfp)
        {
            topicForPost.setPostCount(eChartService.getCountOfPostForTFP(topicForPost.getTopicId()));
        }
        return Result.successCDM(tfp,"获取帖子主题图表数据成功");
    }

    @Autowired
    private DictServiceImpl dictService;
    /**
     * TODO 获取用户图表数据
     */
    @GetMapping("/getUserRoleEChartData")
    public Result getUserRoleEChartData()
    {
        List<Dict> userRole = dictService.getUserRole();
        for (Dict dict:userRole)
        {
            dict.setCount(eChartService.getCountOfUserForDict(dict.getDictTypeId()));
        }
        return Result.successCDM(userRole,"获取用户图表数据成功");
    }

    @Autowired
    private TopicForJobsServiceImpl topicForJobsService;
    /**
     * TODO 获取招聘信息图表数据
     */
    @GetMapping("/getTFJEChartData")
    public Result getTFJEChartData()
    {
        List<TopicForJob> tfj = topicForJobsService.getTopicForJob();
        for (TopicForJob topicForJob:tfj)
        {
            topicForJob.setCount(eChartService.getCountOfJobForTFJ(topicForJob.getTopicId()));
        }
        return Result.successCDM(tfj,"获取招聘信息图表数据成功");
    }

    @Autowired
    private TopicForArticlesServiceImpl topicForArticlesService;
    /**
     * TODO 获取公告信息图表数据
     */
    @GetMapping("/getTFAEChartData")
    public Result getTFAEChartData()
    {
        List<TopicForArticle> tfa = topicForArticlesService.getArticleTopic();
        for (TopicForArticle topicForArticle:tfa)
        {
            topicForArticle.setCount(eChartService.getCountOfArticleForTFA(topicForArticle.getTopicId()));
        }
        return Result.successCDM(tfa,"获取公告信息图表数据成功");
    }


    /**
     * TODO 获取当日活跃用户图表数据---获取昨日活跃用户图表数据
     */
    @GetMapping("/getCountOfTodayDailyUniqueVisitors")
    public Result getCountOfTodayDailyUniqueVisitors()
    {
        Long countOfTodayDailyUniqueVisitorsLog = eChartService.getCountOfTodayDailyUniqueVisitorsLog();
        return Result.successCDM(countOfTodayDailyUniqueVisitorsLog,"获取当日活跃用户图表数据成功");
    }
    @GetMapping("/getCountOfYesterdayDailyUniqueVisitors")
    public Result getCountOfYesterdayDailyUniqueVisitors()
    {
        Long countOfYesterdayDailyUniqueVisitorsLog = eChartService.getCountOfYesterdayDailyUniqueVisitorsLog();
        return Result.successCDM(countOfYesterdayDailyUniqueVisitorsLog,"获取昨日活跃用户图表数据成功");
    }

    /**
     * TODO 获取当月活跃用户图表数据---获取上月活跃用户图表数据
     */
    @GetMapping("/getCountOfCurrentMonthDailyUniqueVisitorsLog")
    public Result getCountOfCurrentMonthDailyUniqueVisitorsLog()
    {
        Long countOfCurrentMonthDailyUniqueVisitorsLog = eChartService.getCountOfCurrentMonthDailyUniqueVisitorsLog();
        return Result.successCDM(countOfCurrentMonthDailyUniqueVisitorsLog,"获取当月活跃用户图表数据成功");
    }
    @GetMapping("/getCountOfLastMonthDailyUniqueVisitors")
    public Result getCountOfLastMonthDailyUniqueVisitors()
    {
        Long countOfLastMonthDailyUniqueVisitorsLog = eChartService.getCountOfLastMonthDailyUniqueVisitorsLog();
        return Result.successCDM(countOfLastMonthDailyUniqueVisitorsLog,"获取上个月活跃用户图表数据成功");
    }


    /**
     * TODO 获取当月公告图表数据---获取上月公告图表数据
     */
    @GetMapping("/getCountOfCurrentMonthArticle")
    public Result getCountOfCurrentMonthArticle()
    {
        Long countOfCurrentMonthArticle = eChartService.getCountOfCurrentMonthArticle();
        return Result.successCDM(countOfCurrentMonthArticle,"获取当月公告图表数据成功");
    }
    @GetMapping("/getCountOfLathMonthArticle")
    public Result getCountOfLathMonthArticle()
    {
        Long countOfLathMonthArticle = eChartService.getCountOfLathMonthArticle();
        return Result.successCDM(countOfLathMonthArticle,"获取上月公告图表数据成功");
    }

    /**
     * TODO 获取当周帖子图表数据---获取上周帖子图表数据
     */
    @GetMapping("/getCountOfCurrentWeekPost")
    public Result getCountOfCurrentWeekPost()
    {
        Long countOfCurrentWeekPost = eChartService.getCountOfCurrentWeekPost();
        return Result.successCDM(countOfCurrentWeekPost,"获取当周帖子图表数据成功");
    }
    @GetMapping("/getCountOfLastWeekPost")
    public Result getCountOfLastWeekPost()
    {
        Long countOfLastWeekPost = eChartService.getCountOfLastWeekPost();
        return Result.successCDM(countOfLastWeekPost,"获取上周帖子图表数据成功");
    }

    /**
     * TODO 获取当月招聘信息图表数据---获取上月招聘信息图表数据
     */
    @GetMapping("/getCountOfCurrentMonthJob")
    public Result getCountOfCurrentMonthJob()
    {
        Long countOfCurrentMonthJob = eChartService.getCountOfCurrentMonthJob();
        return Result.successCDM(countOfCurrentMonthJob,"获取当月招聘信息图表数据成功");
    }
    @GetMapping("/getCountOfLastMonthJob")
    public Result getCountOfLastMonthJob()
    {
        Long countOfLastMonthJob = eChartService.getCountOfLastMonthJob();
        return Result.successCDM(countOfLastMonthJob,"获取上月招聘信息图表数据成功");
    }

    /**
     * TODO 获取当周反馈信息图表数据---获取上周反馈信息图表数据
     */
    @GetMapping("/getCountOfCurrentWeekFeedback")
    public Result getCountOfCurrentWeekFeedback()
    {
        Long countOfCurrentWeekFeedback = eChartService.getCountOfCurrentWeekFeedback();
        return Result.successCDM(countOfCurrentWeekFeedback,"获取当周反馈信息图表数据成功");
    }
    @GetMapping("/getCountOfLastWeekFeedback")
    public Result getCountOfLastWeekFeedback()
    {
        Long countOfLastWeekFeedback = eChartService.getCountOfLastWeekFeedback();
        return Result.successCDM(countOfLastWeekFeedback,"获取上周反馈信息图表数据成功");
    }
}
