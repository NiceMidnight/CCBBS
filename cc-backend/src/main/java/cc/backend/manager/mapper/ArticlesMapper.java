package cc.backend.manager.mapper;

import cc.backend.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @FileName ArticleMapper
 * @Description
 * @Author Tiamo_Null
 * @date 2023-09-02
 **/
@Mapper
public interface ArticlesMapper extends BaseMapper<Article> {
    IPage<Article> selectAllArticle(IPage<Article> iPage,
                                    @Param("userName") String userName,
                                    @Param("articleTitle")String articleTitle,
                                    @Param("articleContent")String articleContent);
    Article getOneByArticleId(@Param("articleId") int articleId);

    int updateArticle(Article article);
}
