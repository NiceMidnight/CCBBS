package cc.backend.manager.service.impl;

import cc.backend.commom.Token;
import cc.backend.entity.Article;
import cc.backend.entity.SearchData;
import cc.backend.manager.mapper.ArticlesMapper;
import cc.backend.manager.service.ArticlesService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @FileName ArticlesServiceImpl
 * @Description
 * @Author Tiamo_Null
 * @date 2023-09-02
 **/
@Service
public class ArticlesServiceImpl implements ArticlesService {
    @Autowired
    private ArticlesMapper articlesMapper;
    @Autowired
    private Token token;
    /**
     * TODO 获取文章
     */
    @Override
    public SearchData getAllArticle(SearchData<Article> searchData) {

        IPage<Article> iPage = new Page<>(searchData.getPageNum(),searchData.getPageSize());
        articlesMapper.selectAllArticle(iPage, searchData.getData().getCreatedBy(), searchData.getData().getArticleTitle(), searchData.getData().getArticleContent());
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * TODO 添加文章
     */
    @Override
    public boolean addArticle(Article articleData, String loginToken) {
        Integer userId = token.TokenInfo(loginToken);
        articleData.setCreatedId(userId);
        articleData.setCreatedTime(new Date());
        System.out.println("时间---------------"+articleData.getUpdatedTime());
        return articlesMapper.insert(articleData) > 0;
    }
    /**
     * TODO 编辑文章
     */
    @Override
    public boolean editArticle(Article article, String loginToken) {
        int userId = token.TokenInfo(loginToken);
        article.setUpdatedId(userId);
        article.setUpdatedTime(new Date());
        System.out.println(article);
        return articlesMapper.updateArticle(article) > 0;
    }

    /**
     * TODO 删除文章
     */
    @Override
    public boolean deleteArticle(Integer articleId) {
        return articlesMapper.deleteById(articleId) > 0;
    }

    /**
     * TODO 查看文章
     */
    @Override
    public Article articleView(int articleId) {
        return articlesMapper.selectById(articleId);
    }

    /**
     * TODO 获取单个文章所有数据
     */
    @Override
    public Article getOneByArticleId(int articleId) {
        Article oneByArticleId = articlesMapper.getOneByArticleId(articleId);
        return oneByArticleId;
    }
}

