package cc.backend.manager.controller;

import cc.backend.commom.Result;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.manager.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * TODO 获取所有帖子内容
     */
    @PostMapping("/getAllPosts")
    public Result getAllPosts(@RequestBody SearchData<Post> searchData){
        System.out.println(searchData);
        SearchData<Post> allPosts = postsService.getAllPosts(searchData);
        return Result.successCDM(allPosts,"获取所有帖子数据成功");
    }
    /**
     * TODO 合规帖子启用
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
     * TODO 不合规帖子禁用
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
     * TODO 查看帖子内容
     */
    @GetMapping("/postView")
    public Result postView(Integer postId) {
        Post post = postsService.postView(postId);
        return Result.successCDM(post,"查看帖子"+postId+"内容成功");
    }
}
