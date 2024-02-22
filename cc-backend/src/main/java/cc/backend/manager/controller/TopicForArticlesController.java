package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.TopicForArticle;
import cc.backend.manager.service.TopicForArticlesService;
import cc.backend.manager.service.impl.TopicForArticlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/22
 */
@RestController
@RequestMapping("/manager/tfa")
public class TopicForArticlesController {
    @Autowired
    private TopicForArticlesServiceImpl topicForArticlesService;
    @GetMapping("/getArticleTopic")
    public Result getArticleTopic()
    {
        List<TopicForArticle> articleTopic = topicForArticlesService.getArticleTopic();
        return Result.successCDM(articleTopic,"获取主题成功");
    }
}
