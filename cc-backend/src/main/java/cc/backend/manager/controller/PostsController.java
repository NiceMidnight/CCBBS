package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.enums.CommentStatusForCompliance;
import cc.backend.enums.PostStatus;
import cc.backend.manager.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/24
 */
@RestController
@RequestMapping("/manager/post")
public class PostsController {
    @Autowired
    private PostsService postsService;
    /**
     * @description TODO 获取所有帖子内容
     * @param searchData
     * @param startTime
     * @param endTime
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getAllPosts")
    public Result getAllPosts(@RequestBody SearchData<Post> searchData,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
    {
        System.out.println(searchData);
        SearchData<Post> allPosts = postsService.getAllPosts(searchData,startTime,endTime);
        return Result.successCDM(allPosts,"获取所有帖子数据成功");
    }
    /**
     * @description TODO 获取帖子状态
     *
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getPostStatusForComplianceOption")
    public Result getPostStatusForComplianceOption()
    {
        List<PostStatus> postStatuses = Arrays.asList(PostStatus.values());
        return Result.successCDM(postStatuses,"帖子状态获取成功");
    }
    /**
     * @description TODO 合规帖子启用
     * @param postId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/compliancePost")
    public Result compliancePost(Integer postId) {
        boolean b = postsService.compliancePost(postId);
        if (b) {
            return Result.successCM("合规帖子启用成功");
        }
        return Result.error("合规帖子启用失败");
    }

    /**
     * @description TODO 不合规帖子禁用
     * @param postId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/irregularityPost")
    public Result irregularityPost(Integer postId) {
        boolean b = postsService.irregularityPost(postId);
        if (b) {
            return Result.successCM("不合规帖子禁用成功");
        }
        return Result.error("不合规帖子禁用失败");
    }
    /**
     * @description TODO 查看帖子内容
     * @param postId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/postView")
    public Result postView(Integer postId) {
        Post post = postsService.postView(postId);
        return Result.successCDM(post,"查看帖子"+postId+"内容成功");
    }

    @GetMapping("/getPostTitle")
    public Result getPostTitle(@RequestParam("postId") Integer postId)
    {
        String postTitle = postsService.getPostTitle(postId);
        return Result.successCDM(postTitle,"获取帖子标题成功");
    }
}
