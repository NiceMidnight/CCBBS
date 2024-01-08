package cc.backend.user.controller;

import cc.backend.commom.Result;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.user.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * TODO 获取所有帖子信息
     */
    @PostMapping("/getAllPost")
    public Result getAllPost(@RequestBody SearchData<Post> queryCondition, @RequestParam String postMsg) {
        System.out.println(postMsg);
        System.out.println(queryCondition);
        SearchData<Post> allPost = postService.getAllPost(queryCondition, postMsg);
        return Result.successCDM(allPost,"获取帖子信息成功");
    }

    /**
     * TODO 通过主题获取帖子信息
     */
    @PostMapping("/getPostByTopic")
    public Result getPostByTopic(@RequestBody SearchData<Post> queryCondition) {
        System.out.println(queryCondition);
        SearchData<Post> postByTopic = postService.getPostByTopic(queryCondition);
        return Result.successCDM(postByTopic,"错误");
    }
}
