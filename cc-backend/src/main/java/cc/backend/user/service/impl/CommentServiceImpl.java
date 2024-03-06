package cc.backend.user.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.Comments;
import cc.backend.entity.SearchData;
import cc.backend.enums.CommentStatusForUser;
import cc.backend.user.mapper.CommentMapper;
import cc.backend.user.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/1/14
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Token token;
    /**
     * @description TODO 获取评论数量
     * @param postId
     * @return: int
     */
    @Override
    public int getCommentCount(int postId) {
        QueryWrapper<Comments> commentsQueryWrapper = new QueryWrapper<>();
        commentsQueryWrapper.eq("post_id",postId);
        return Math.toIntExact(commentMapper.selectCount(commentsQueryWrapper));
    }


    /**
     * @description TODO 添加评论
     * @param comments
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean insertComment(Comments comments,String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        comments.setUserId(userId);
        comments.setCreatedAt(new Date());
        return commentMapper.insert(comments) > 0;
    }

    /**
     * @description TODO 获取所有最新评论
     * @Param postId
     * @return: java.util.List<cc.backend.entity.Comments>
     */
    @Override
    public List<Comments> getAllCommentByCreatedAt(Integer postId) {
        List<Comments> allComments = commentMapper.getAllCommentByCreatedAt(postId);
        return filteredComments(allComments);
    }

    /**
     * @description TODO 获取所有最热评论
     * @param postId
     * @return: java.util.List<cc.backend.entity.Comments>
     */
    @Override
    public List<Comments> getAllCommentByTop(Integer postId) {
        List<Comments> allComments = commentMapper.getAllCommentByTop(postId);
        return filteredComments(allComments);
    }

    /**
     * @description TODO 插入回复评论
     * @param comments
     * @param tokenInfo
     * @return: boolean
     */
    @SneakyThrows
    @Override
    public boolean submitAReply(Comments comments, String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        comments.setUserId(userId);
        comments.setCreatedAt(new Date());
        return commentMapper.insert(comments) > 0;
    }

    /**
     * @description TODO 删除个人评论
     * @param commentId
     * @param tokenInfo
     * @return: boolean
     */
    @Override
    public boolean deletePersonalComment(Integer commentId, String tokenInfo) {
        Comments comments = commentMapper.selectById(commentId);
        int userId = token.getUserId(tokenInfo);
        if (comments.getUserId() == userId)
        {
            return commentMapper.deleteById(commentId) > 0;
        }
        return false;
    }

    /**
     * @description TODO 获取未读回复评论
     * @param searchData
     * @param tokenInfo
     * @return: cc.backend.entity.SearchData<cc.backend.entity.Comments>
     */
    @Override
    public SearchData<Comments> getReplyComments(SearchData<Comments> searchData,String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        IPage<Comments> iPage = new Page<>(searchData.getPageNum(), searchData.getPageSize());
        commentMapper.selectReplyComments(iPage,userId);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(), iPage.getRecords());
    }

    /**
     * @description TODO 获取未读回复数量
     * @param tokenInfo
     * @return: int
     */
    @Override
    public int getReplyCommentsCount(String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        return commentMapper.selectReplyCommentsCount(userId);
    }

    /**
     * @description TODO 用户已查看
     * @param commentId
     * @param commentStatusForUser
     * @return: boolean
     */
    @Override
    public boolean changeStatusForUser(Integer commentId, CommentStatusForUser commentStatusForUser) {
        return commentMapper.updateStatusForUser(commentId,commentStatusForUser) > 0;
    }

    /**
     * @description TODO 过滤设置对应父评论的子评论
     * @param allComments
     * @return: java.util.List<cc.backend.entity.Comments>
     */
    private List<Comments> filteredComments(List<Comments> allComments) {
        List<Comments> filteredComments = new ArrayList<>();
        Map<Integer, List<Comments>> childCommentsMap = new HashMap<>();
        // 先找出所有父评论（没有parentId的评论），并将其加入filteredComments
        for (Comments comment : allComments) {
            Integer parentId = comment.getParentCommentId();
            if (parentId == null) {
                filteredComments.add(comment);
            } else {
                childCommentsMap.putIfAbsent(parentId, new ArrayList<>());
                childCommentsMap.get(parentId).add(comment);
            }
        }
        System.out.println(childCommentsMap);
        // 为每个父评论设置对应的子评论列表
        for (Comments parentComment : filteredComments) {
            setChildComments(parentComment, childCommentsMap);
        }
        return filteredComments;
    }

    /**
     * @description TODO 递归函数，为父评论设置子评论列表
     * @param parentComment
     * @param childCommentsMap
     * @return: void
     */
    private void setChildComments(Comments parentComment, Map<Integer, List<Comments>> childCommentsMap) {
        Integer parentId = parentComment.getCommentId();
        if (childCommentsMap.containsKey(parentId)) {
            List<Comments> childComments = childCommentsMap.get(parentId);
            parentComment.setChildCommentsList(childComments);
            // 递归处理子评论的子评论
            for (Comments childComment : childComments) {
                setChildComments(childComment, childCommentsMap);
            }
        }
    }
}
