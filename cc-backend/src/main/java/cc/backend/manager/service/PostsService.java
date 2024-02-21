package cc.backend.manager.service;

import cc.backend.entity.Post;
import cc.backend.entity.SearchData;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/24
 */
public interface PostsService {
    SearchData<Post> getAllPosts(SearchData<Post> searchData, LocalDateTime startTime, LocalDateTime endTime);
    boolean compliancePost(Integer postId);
    boolean irregularityPost(Integer postId);
    Post postView(Integer postId);
    String getPostTitle(Integer postId);
}
