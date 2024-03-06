package cc.backend.user.service;

import cc.backend.entity.Post;
import cc.backend.entity.SearchData;

import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/17
 */
public interface PostService {
    SearchData<Post> getAllPost(SearchData<Post> queryCondition,String postMsg);
    SearchData<Post> getPostByTopic(SearchData<Post> queryCondition);
    Post getPostByPostId(Integer postId);
    Post updatePostLikesCount(Integer postId,boolean addOrDel);
    Post updatePostDislikesCount(Integer postId,boolean addOrDel);
    boolean updatePostCommentCount(Integer postId,boolean addOrDel);
    boolean increaseViewCount(Integer postId);
    List<Post> getTopPosts();
    boolean insertAPost(Post post,String tokenInfo);
    SearchData<Post> getPostByUserId(SearchData<Post> searchData,Integer userId);
}
