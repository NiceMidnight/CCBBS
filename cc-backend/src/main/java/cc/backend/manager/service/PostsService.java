package cc.backend.manager.service;

import cc.backend.entity.Post;
import cc.backend.entity.SearchData;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/24
 */
public interface PostsService {
    SearchData<Post> getAllPosts(SearchData<Post> searchData);
    boolean compliancePost(Integer postId);
    boolean irregularityPost(Integer postId);
    Post postView(Integer postId);
}
