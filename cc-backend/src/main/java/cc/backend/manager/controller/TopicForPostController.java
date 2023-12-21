package cc.backend.manager.controller;

import cc.backend.commom.Result;
import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.TopicForPost;
import cc.backend.manager.service.impl.TopicForPostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
@RestController
@RequestMapping("/manager/tfp")
public class TopicForPostController {
    @Autowired
    private TopicForPostServiceImpl topicForPostService;
    /**
     * TODO 获取主题内容
     */
    @PostMapping("/getTFP")
    public Result getTFP(@RequestBody SearchData<TopicForPost> queryCondition) {
        SearchData<TopicForPost> tfp = topicForPostService.getTFP(queryCondition);
        return Result.successCDM(tfp,"获取帖子主题内容成功");
    }
    /**
     * TODO 启用主题
     */
    @GetMapping("/enableTFP")
    public Result enableTFP(@RequestParam Integer topicId,@RequestHeader("Authorization") String tokenInfo) {
        boolean enableTFP = topicForPostService.enableTFP(topicId,tokenInfo);
        if (enableTFP) {
            return Result.successCDM(topicId,"启用成功");
        }
        return Result.error("启用失败");
    }
    /**
     * TODO 禁用主题
     */
    @GetMapping("/disableTFP")
    public Result disableTFP(@RequestParam Integer topicId,@RequestHeader("Authorization") String tokenInfo) {
        boolean disableTFP = topicForPostService.disableTFP(topicId,tokenInfo);
        if (disableTFP) {
            return Result.successCDM(topicId,"禁用成功");
        }
        return Result.error("禁用失败");
    }
}
