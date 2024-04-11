package cc.backend.manager.mapper;

import cc.backend.entity.Article;
import cc.backend.enums.ArticleStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

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
                                    @Param("articleContent")String articleContent,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime")LocalDateTime endTime);
    Article getOneByArticleId(@Param("articleId") int articleId);

    int updateArticle(Article article);
    int updateArticleStatusByArticleId(@Param("articleId")Integer articleId, @Param("articleStatus")ArticleStatus articleStatus);

    @Select("SELECT COUNT(*) FROM article WHERE YEAR(created_time) = YEAR(CURDATE()) " +
            "AND MONTH(created_time) = MONTH(CURDATE())")
    Long countByCurrentMonth();

    @Select("SELECT COUNT(*) FROM article WHERE YEAR(created_time) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND MONTH(created_time) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)")
    Long countByLastMonth();
}
