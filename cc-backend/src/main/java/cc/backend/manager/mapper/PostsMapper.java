package cc.backend.manager.mapper;

import cc.backend.entity.Post;
import cc.backend.enums.PostStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/24
 */
@Mapper
public interface PostsMapper extends BaseMapper<Post> {
    IPage<Post> selectAllPost(IPage<Post> iPage,
                              @Param("userName") String userName,
                              @Param("postTitle") String postTitle,
                              @Param("postContent") String postContent);
    int updatePostStatus(@Param("post_id")int postId,@Param("post_status") PostStatus postStatus);
}
