package cc.backend.user.mapper;

import cc.backend.entity.Post;
import cc.backend.enums.PostStatus;
import cc.backend.enums.PostVisibility;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/17
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    IPage<Post> selectAllPost(IPage<Post> iPage,
                              @Param("topicId") Integer topicId,
                              @Param("postMsg") String postMsg,
                              @Param("postVisibility")PostVisibility postVisibility,
                              @Param("postStatus")PostStatus postStatus);

    IPage<Post> getAllPostByTopicId(IPage<Post> iPage,Post post);
    Post getPostByPostId(@Param("postId") Integer postId);
    List<Post> getTopPosts();
    IPage<Post> selectAllByUserId(IPage<Post> iPage, Post post);
//    @Param("postTitle")String postTitle,
//    @Param("userId")Integer userId,
//    @Param("topicId")Integer topicId,
//    @Param("postVisibility")PostVisibility postVisibility,
//    @Param("postStatus")PostStatus postStatus
}
