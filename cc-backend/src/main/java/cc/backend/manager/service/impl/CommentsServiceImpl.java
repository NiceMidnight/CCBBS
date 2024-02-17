package cc.backend.manager.service.impl;

import cc.backend.entity.Article;
import cc.backend.entity.Comments;
import cc.backend.entity.SearchData;
import cc.backend.enums.CommentStatusForCompliance;
import cc.backend.manager.mapper.CommentsMapper;
import cc.backend.manager.service.CommentsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/17
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    /**
     * @description TODO 通过查询条件获取帖子数据
     * @param commentsSearchData
     * @param startTime
     * @param endTime
     * @return: cc.backend.entity.SearchData<cc.backend.entity.Comments>
     */
    @Override
    public IPage<Comments> getComments(SearchData<Comments> commentsSearchData, LocalDateTime startTime, LocalDateTime endTime)
    {
        IPage<Comments> iPage = new Page<>(commentsSearchData.getPageNum(),commentsSearchData.getPageSize());
        IPage<Comments> commentsIPage = commentsMapper.selectAllBySearchData(iPage, commentsSearchData.getData(), startTime, endTime);
        return commentsIPage;
    }

    /**
     * @description TODO 更新评论状态是否合规
     * @param commentId
     * @param statusForCompliance
     * @return: boolean
     */
    @Override
    public boolean updateCommentStatusForCompliance(Integer commentId, CommentStatusForCompliance statusForCompliance)
    {
        return commentsMapper.updateCommentStatusForComplianceById(commentId,statusForCompliance) > 0;
    }

    /**
     * @description TODO 获取单个评论视图
     * @param commentId
     * @return: cc.backend.entity.Comments
     */
    @Override
    public Comments getOneComment(Integer commentId) {
        return commentsMapper.selectById(commentId);
    }
}
