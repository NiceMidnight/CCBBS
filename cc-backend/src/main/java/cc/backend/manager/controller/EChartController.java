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
}
