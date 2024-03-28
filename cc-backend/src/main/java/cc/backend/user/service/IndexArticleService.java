package cc.backend.user.service;

import cc.backend.entity.Article;
import cc.backend.entity.SearchData;

import java.util.List;

/**
 * @FileName IndexArticleService
 * @Description 首页文章显示
 * @Author Tiamo_Null
 * @date 2023-09-03
 **/
public interface IndexArticleService {
    SearchData<Article> getIndexArticle(SearchData<Article> pageData);
    List<Article> getHotArticle();
    Article getArticle(int articleId);
}
