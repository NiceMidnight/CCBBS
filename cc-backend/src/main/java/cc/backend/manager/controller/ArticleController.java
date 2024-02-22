package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.Article;
import cc.backend.entity.SearchData;
import cc.backend.enums.ArticleStatus;
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
     * @description TODO 获取所有文章内容
     * @param searchData
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getAllArticle")
    public Result getAllArticle(@RequestBody SearchData<Article> searchData){
        SearchData<Article> allArticle = articleService.getAllArticle(searchData);
        return Result.successCDM(allArticle,"获取文章数据成功");
    }
    /**
     * @description TODO 添加文章
     * @param articleData
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody Article articleData,@RequestHeader("Authorization") String tokenInfo) {
        System.out.println(articleData);
        boolean addArticle = articleService.addArticle(articleData, tokenInfo);
        if (addArticle) {
            return Result.successCM("添加文章成功");
        }
        return Result.error("添加文章失败");
    }
    /**
     * @description TODO 删除文章
     * @param id
     * @return: cc.backend.common.Result
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
     * @description TODO 编辑文章
     * @param article
     * @param tokenInfo
     * @return: cc.backend.common.Result
     */
    @PostMapping("/editArticle")
    public Result editArticle(@RequestBody Article article,@RequestHeader("Authorization")String tokenInfo) {
        if (article != null && article.getTopicId() == null) {
            return Result.error("编辑公告" + article.getArticleId() + "失败");
        }
        boolean editArticle = articleService.editArticle(article, tokenInfo);
        if (editArticle) {
            return Result.successCM("编辑公告"+article.getArticleId()+"成功");
        }
        return Result.error("编辑公告"+article.getArticleId()+"失败");
    }
    /**
     * @description TODO 查看文章
     * @param articleId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/articleView")
    public Result articleView (@RequestParam int articleId) {
        System.out.println(articleId);
        Article article = articleService.articleView(articleId);
        return Result.successCDM(article,"获取文章成功");
    }
    /**
     * @description TODO 单个文章所有数据
     * @param articleId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getOneByArticleId")
    public Result getOneByArticleId(@RequestParam int articleId) {
        Article oneByArticleId = articleService.getOneByArticleId(articleId);
        return Result.successCDM(oneByArticleId,"文章数据查找成功");
    }

    /**
     * @description TODO 使公告可见
     * @param articleId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/makeAnArticlePublic")
    public Result makeAnArticlePublic(@RequestParam("articleId")Integer articleId)
    {
        boolean isMakeAnArticlePublic = articleService.updateAnArticleStatusById(articleId, ArticleStatus.PUBLIC);
        if (isMakeAnArticlePublic)
        {
            return Result.successCM("公开可见成功");
        }
        return Result.error("公开可见失败");
    }

    /**
     * @description TODO 使公告不可见
     * @param articleId
     * @return: cc.backend.common.Result
     */
    @GetMapping("/makeAnArticlePrivate")
    public Result makeAnArticlePrivate(@RequestParam("articleId")Integer articleId)
    {
        boolean isMakeAnArticlePrivate = articleService.updateAnArticleStatusById(articleId,ArticleStatus.PRIVATE);
        if (isMakeAnArticlePrivate)
        {
            return Result.successCM("私有不可见成功");
        }
        return Result.error("私有不可见失败");
    }

}
