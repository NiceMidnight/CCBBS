package cc.backend.user.mapper;

import cc.backend.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @FileName IndexArticleMapper
 * @Description
 * @Author Tiamo_Null
 * @date 2023-09-03
 **/
@Mapper
public interface IndexArticleMapper extends BaseMapper<Article> {
    Page<Article> getAllByArticleTitle(IPage articlePage, @Param("articleTitle")String articleTitle,@Param("topicId")Integer topicId);
    List<Article> selectHotArticle();
    Article getOneByArticleIdArticle(@Param("articleId") int articleId);
}
