package cc.backend.user.service.impl;

import cc.backend.entity.Comments;
import cc.backend.user.mapper.CommentMapper;
import cc.backend.user.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/14
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public int getCommentCount(int postId) {
        QueryWrapper<Comments> commentsQueryWrapper = new QueryWrapper<>();
        commentsQueryWrapper.eq("post_id",postId);
        int count = Math.toIntExact(commentMapper.selectCount(commentsQueryWrapper));
        return count;
    }
}
