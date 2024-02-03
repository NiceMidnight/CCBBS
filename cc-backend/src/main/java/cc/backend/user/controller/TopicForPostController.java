package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.forpost.TopicForPost;
import cc.backend.user.service.impl.TopicForPostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 帖子主题
 * @Author Tiamo_null
 * @Date 2023/12/22
 */
@RestController
@RequestMapping("/user/tfp")
public class TopicForPostController {
    @Autowired
    private TopicForPostServiceImpl topicForPostService;
    /**
     * TODO 获取帖子主题所有信息
     */
    @GetMapping("/getPostTopic")
    public Result getTFP() {
        List<TopicForPost> tfp = topicForPostService.getPostTopic();
        if (tfp != null) {
            return Result.successCDM(tfp,"获取帖子主题成功");
        } else return Result.error("获取帖子主题失败");
    }


}
