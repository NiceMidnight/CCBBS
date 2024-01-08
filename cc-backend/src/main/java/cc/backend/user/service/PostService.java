package cc.backend.user.service;

import cc.backend.entity.Post;
import cc.backend.entity.SearchData;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/17
 */
public interface PostService {
    SearchData<Post> getAllPost(SearchData<Post> queryCondition,String postMsg);
    SearchData<Post> getPostByTopic(SearchData<Post> queryCondition);
}
