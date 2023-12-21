package cc.backend.manager.controller;

import cc.backend.commom.Result;
import cc.backend.entity.Article;
import cc.backend.entity.SearchData;
import cc.backend.manager.service.impl.ArticlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName ArticleController
 * @Description
 * @Author Tiamo_Null
 * @date 2023-09-02
 **/
@RestController
@RequestMapping("/manager/article")
public class ArticleController {
    @Autowired
    private ArticlesServiceImpl articleService;
    /**
     * TODO 获取所有文章内容
     */
    @PostMapping("/getAllArticle")
    public Result getAllArticle(@RequestBody SearchData<Article> searchData){
        SearchData allArticle = articleService.getAllArticle(searchData);
        return Result.successCDM(allArticle,"获取文章数据成功");
    }
    /**
     * TODO 添加文章
     */
    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody Article articleData, @RequestParam("loginToken") String loginToken) {
        System.out.println(articleData);
        boolean addArticle = articleService.addArticle(articleData, loginToken);
        if (addArticle) {
            return Result.successCM("添加文章成功");
        }
        return Result.error("添加文章失败");
    }
    /**
     * TODO 删除文章
     */
    @PostMapping("/deleteArticle")
    public Result deleteArticle(@RequestParam Integer id) {
        boolean deleteArticle = articleService.deleteArticle(id);
        if (deleteArticle) {
            return Result.successCM("文章"+id+"删除成功");
        }
        return Result.error("文章"+id+"删除失败");
    }
    /**
     * TODO 编辑文章
     */
    @PostMapping("/editArticle")
    public Result editArticle(@RequestBody Article article,@RequestParam("loginToken")String loginToken) {
        boolean editArticle = articleService.editArticle(article, loginToken);
        if (editArticle) {
            return Result.successCM("编辑成功");
        }
        return Result.error("编辑失败");
    }
    /**
     * TODO 查看文章
     */
    @GetMapping("/articleView")
    public Result articleView (@RequestParam int articleId) {
        System.out.println(articleId);
        Article article = articleService.articleView(articleId);
        return Result.successCDM(article,"获取文章成功");
    }
    /**
     * TODO 单个文章所有数据
     */
    @GetMapping("/getOneByArticleId")
    public Result getOneByArticleId(@RequestParam int articleId) {
        Article oneByArticleId = articleService.getOneByArticleId(articleId);
        return Result.successCDM(oneByArticleId,"文章数据查找成功");
    }
}
