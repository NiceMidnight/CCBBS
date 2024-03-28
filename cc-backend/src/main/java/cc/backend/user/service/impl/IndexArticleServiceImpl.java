package cc.backend.user.service.impl;

import cc.backend.entity.Article;
import cc.backend.entity.SearchData;
import cc.backend.user.mapper.IndexArticleMapper;
import cc.backend.user.service.IndexArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName IndexArticleServiceImpl
 * @Description
 * @Author Tiamo_Null
 * @date 2023-09-03
 **/
@Service
public class IndexArticleServiceImpl implements IndexArticleService {

    @Autowired
    private IndexArticleMapper articleMapper;
    /**
     * TODO 获取首页公告
     */
    @Override
    public SearchData<Article> getIndexArticle(SearchData<Article> pageData) {
        IPage<Article> articlePage = new Page<>(pageData.getPageNum(), pageData.getPageSize());
        articleMapper.getAllByArticleTitle(articlePage,pageData.getData().getArticleTitle());
        return SearchData.pageData(
                (int) articlePage.getCurrent(),
                (int) articlePage.getSize(),
                (int) articlePage.getTotal(),
                articlePage.getRecords());
    }

    /**
     * TODO 获取热门公告
     */
    @Override
    public List<Article> getHotArticle() {

        return articleMapper.selectHotArticle();
    }

    /**
     * TODO 获取单个公告内容
     */
    @Override
    public Article getArticle(int articleId) {
        return articleMapper.getOneByArticleIdArticle(articleId);
    }
}
