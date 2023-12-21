package cc.backend.manager.service;

import cc.backend.entity.Article;
import cc.backend.entity.SearchData;

/**
 * @FileName ArticleService
 * @Description
 * @Author Tiamo_Null
 * @date 2023-09-02
 **/
public interface ArticlesService {
    SearchData getAllArticle(SearchData<Article> searchData);
    boolean addArticle(Article articleData,String loginToken);
    boolean editArticle(Article article,String loginToken);
    boolean deleteArticle(Integer articleId);
    Article articleView(int articleId);
    Article getOneByArticleId(int articleId);
}
