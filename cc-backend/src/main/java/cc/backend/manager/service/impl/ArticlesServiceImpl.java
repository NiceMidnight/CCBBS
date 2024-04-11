package cc.backend.manager.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.Article;
import cc.backend.entity.SearchData;
import cc.backend.enums.ArticleStatus;
import cc.backend.manager.mapper.ArticlesMapper;
import cc.backend.manager.service.ArticlesService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public SearchData<Article> getAllArticle(SearchData<Article> searchData, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Article> iPage = new Page<>(searchData.getPageNum(),searchData.getPageSize());
        articlesMapper.selectAllArticle(iPage,
                searchData.getData().getCreatedBy(),
                searchData.getData().getArticleTitle(),
                searchData.getData().getArticleContent(),
                startTime,endTime);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * @description TODO 添加文章
     * @param articleData
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean addArticle(Article articleData, String tokenInfo) {
        Integer userId = token.getUserId(tokenInfo);
        articleData.setCreatedId(userId);
        articleData.setCreatedTime(new Date());
        return articlesMapper.insert(articleData) > 0;
    }
    /**
     * @description TODO 编辑文章
     * @param article
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean editArticle(Article article, String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        article.setUpdatedId(userId);
        article.setUpdatedTime(new Date());
        return articlesMapper.updateArticle(article) > 0;
    }

    /**
     * @description TODO 删除文章
     * @param articleId
     * @return: boolean
     */
    @Override
    public boolean deleteArticle(Integer articleId) {
        return articlesMapper.deleteById(articleId) > 0;
    }

    /**
     * @description TODO 查看文章
     * @param articleId
     * @return: cc.backend.entity.Article
     */
    @Override
    public Article articleView(Integer articleId) {
        return articlesMapper.selectById(articleId);
    }

    /**
     * @description TODO 获取单个文章所有数据
     * @param articleId
     * @return: cc.backend.entity.Article
     */
    @Override
    public Article getOneByArticleId(Integer articleId) {
        return articlesMapper.getOneByArticleId(articleId);
    }

    /**
     * @description TODO 更新公告状态
     * @param articleId
     * @return: boolean
     */
    @Override
    public boolean updateAnArticleStatusById(Integer articleId, ArticleStatus articleStatus) {
        return articlesMapper.updateArticleStatusByArticleId(articleId,articleStatus) > 0;
    }
}

