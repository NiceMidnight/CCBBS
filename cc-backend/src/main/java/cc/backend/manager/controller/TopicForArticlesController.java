package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.TopicForArticle;
import cc.backend.enums.TopicStatus;
import cc.backend.manager.service.TopicForArticlesService;
import cc.backend.manager.service.impl.TopicForArticlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 公告主题
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
@RestController
@RequestMapping("/manager/tfa")
public class TopicForArticlesController {
    @Autowired
    private TopicForArticlesServiceImpl topicForArticlesService;

    /**
     * TODO 获取公告主题
     */
    @GetMapping("/getArticleTopic")
    public Result getArticleTopic()
    {
        List<TopicForArticle> articleTopic = topicForArticlesService.getArticleTopic();
        return Result.successCDM(articleTopic,"获取主题成功");
    }

    @GetMapping("/enableTopicForArticle")
    public Result enableTopicForArticle(@RequestParam("topicId")Integer topicId)
    {
        boolean isUpdateTopicStatus = topicForArticlesService.updateTopicForArticleStatus(topicId, TopicStatus.ENABLE);
        if (isUpdateTopicStatus)
        {
            return Result.successCM("启用公告主题成功（id："+topicId+")");
        }
        return Result.error("启用公告主题失败（id："+topicId+")");
    }

    @GetMapping("/disableTopicForArticle")
    public Result disableTopicForArticle(@RequestParam("topicId")Integer topicId)
    {
        boolean isUpdateTopicStatus = topicForArticlesService.updateTopicForArticleStatus(topicId, TopicStatus.DISABLE);
        if (isUpdateTopicStatus)
        {
            return Result.successCM("禁用公告主题成功（id："+topicId+")");
        }
        return Result.error("禁用公告主题失败（id："+topicId+")");
    }

    @GetMapping("/updateTopicColor")
    public Result updateTopicColor(@RequestParam("topicId")Integer topicId,@RequestParam("topicColor")String topicColor)
    {
        boolean isUpdateTopicColor = topicForArticlesService.updateTopicColor(topicId, topicColor);
        if (isUpdateTopicColor)
        {
            return Result.successCM("更新公告主题颜色成功（id："+topicId+")");
        }
        return Result.error("更新公告主题颜色失败（id："+topicId+")");
    }

    @PostMapping("/addTopic")
    public Result addTopic(@RequestBody TopicForArticle topicForArticle,
                           @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isAddTopic = topicForArticlesService.addTopic(topicForArticle, tokenInfo);
        if (isAddTopic)
        {
            return Result.successCM("添加公告主题成功");
        }
        return Result.error("添加公告主题失败");
    }
}
