package cc.backend.entity;

import cc.backend.enums.ArticleStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @FileName Article
 * @Description 文章
 * @Author Tiamo_Null
 * @date 2023-09-02
 **/
@Data
@TableName("article")
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    /**
     * 文章id
     */
    @TableId(value = "article_id",type = IdType.AUTO)
    private Integer articleId;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 文章内容
     */
    private String articleContent;
    /**
     * 创建者id
     */
    private Integer createdId;
    /**
     * 创建者
     */
    @TableField(exist = false)
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新者id
     */
    private Integer updatedId;
    /**
     * 更新者
     */
    @TableField(exist = false)
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 文章阅读量
     */
    private Integer viewCount;
    /**
     * 文章所属主题id
     */
    private Integer topicId;
    /**
     * 文章所属主题名称
     */
    @TableField(exist = false)
    private String topicName;
    /**
     * 文章所属主题颜色
     */
    @TableField(exist = false)
    private String topicColor;
    /**
     * 文章状态可见/不可见
     */
    private ArticleStatus articleStatus;

}
