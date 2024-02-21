package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.SearchData;
import cc.backend.entity.forpost.TopicForPost;
import cc.backend.enums.TopicStatus;
import cc.backend.manager.service.impl.TopicForPostsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 帖子主题
 * @Author Tiamo_null
 * @Date 2023/12/17
 */
@RestController
@RequestMapping("/manager/tfp")
public class TopicForPostsController {
    @Autowired
    private TopicForPostsServiceImpl topicForPostService;
    /**
     * @description TODO 获取主题内容
     * @param queryCondition
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getTFP")
    public Result getTFP(@RequestBody SearchData<TopicForPost> queryCondition) {
        SearchData<TopicForPost> tfp = topicForPostService.getTFP(queryCondition);
        return Result.successCDM(tfp,"获取帖子主题内容成功");
    }
    /**
     * @description TODO 获取主题状态
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getTFPStatus")
    public Result getTFPStatus() {
        return Result.successCDM(TopicStatus.values(),"获取主题状态成功");
    }
    /**
     * @description TODO 添加主题
     * @param topicForPost
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @PostMapping("/addTFP")
    public Result addTFP(@RequestBody TopicForPost topicForPost,@RequestHeader("Authorization") String tokenInfo) {
        boolean addTFP = topicForPostService.addTFP(topicForPost,tokenInfo);
        return Result.successCDM(addTFP,"添加主题成功");
    }

    /**
     * @description TODO 启用主题
     * @param topicId
     * @param tokenInfo
     * @return: cc.backend.common.Result
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
     * @description TODO 禁用主题
     * @param topicId
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/disableTFP")
    public Result disableTFP(@RequestParam Integer topicId,@RequestHeader("Authorization") String tokenInfo) {
        boolean disableTFP = topicForPostService.disableTFP(topicId,tokenInfo);
        if (disableTFP) {
            return Result.successCDM(topicId,"禁用成功");
        }
        return Result.error("禁用失败");
    }
    /**
     * @description TODO 通过id获取主题内容
     * @param topicId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getTopicById")
    public Result getTopicById(@RequestParam("topicId")Integer topicId)
    {
        TopicForPost topic = topicForPostService.getTopicById(topicId);
        if (topic != null)
        {
            return Result.successCDM(topic,"获取主题内容成功");
        }
        return  Result.error("获取主题内容失败");
    }

    /**
     * @description TODO 更新主题
     * @param topicName
     * @param topicId
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @GetMapping("/editTopic")
    public Result editTopic(@RequestParam("topicName")String topicName,
                            @RequestParam("topicId")Integer topicId,
                            @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isEdit = topicForPostService.editTopic(topicName, topicId, tokenInfo);
        if (isEdit)
        {
            return Result.successCM("编辑主题id:"+topicId+"成功");
        }
        return Result.error("编辑主题id:"+topicId+"失败");
    }
}
