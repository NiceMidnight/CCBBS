package cc.backend.user.controller;

import cc.backend.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 文章主题
 * @Author Tiamo_null
 * @Date 2023/12/22
 */
@RestController
@RequestMapping("/user/tfa")
public class TopicForArticleController {
    @RequestMapping("/getTFA")
    public Result getTFA() {
        return Result.error("获取公告文章主题失败");
    }
}
