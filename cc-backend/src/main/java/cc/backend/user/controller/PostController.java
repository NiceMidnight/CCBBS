package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.user.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/12/22
 */
@RestController
@RequestMapping("/user/post")
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    /**
     * @description TODO 获取所有帖子信息
     * @param: @param queryCondition 
     * @param postMsg
     * @return: cc.backend.common.Result
     */ 
    @PostMapping("/getAllPost")
    public Result getAllPost(@RequestBody SearchData<Post> queryCondition, @RequestParam String postMsg) {
        System.out.println(postMsg);
        System.out.println(queryCondition);
        SearchData<Post> allPost = postService.getAllPost(queryCondition, postMsg);
        return Result.successCDM(allPost,"获取帖子信息成功");
    }

    /**
     * @description: 通过主题获取帖子信息
     * @param: @param queryCondition
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getPostByTopic")
    public Result getPostByTopic(@RequestBody SearchData<Post> queryCondition) {
        System.out.println(queryCondition);
        SearchData<Post> postByTopic = postService.getPostByTopic(queryCondition);
        return Result.successCDM(postByTopic,"通过主题获取帖子信息成功");
    }

    /**
     * @description TODO 通过帖子id获取帖子内容
     * @param postId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getPostByPostId")
    public Result getPostByPostId(@RequestParam("postId") Integer postId) {
        Post postByPostId = postService.getPostByPostId(postId);
        if (postByPostId != null) {
            return Result.successCDM(postByPostId,"获取帖子"+postId+"内容成功");
        } else return Result.error("获取帖子"+postId+"内容失败");
    }
    /**
     * @description TODO 添加阅读量（点击量）
     * @param postId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/increaseViewCount")
    public Result increaseViewCount(@RequestParam("postId") Integer postId)
    {
        boolean isIncrease = postService.increaseViewCount(postId);
        if (isIncrease)
        {
            return Result.successCM("添加阅读量成功");
        }
        return Result.error("添加阅读量失败");
    }

    /**
     * @description TODO 获取实时热点帖子
     *
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getTopPost")
    public Result getTopPost()
    {
        List<Post> topPosts = postService.getTopPosts();
        if (topPosts != null)
        {
            return Result.successCDM(topPosts,"获取到最热帖子");
        }
        return Result.successCM("没有最热评论");

    }
}
