package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.Comments;
import cc.backend.entity.SearchData;
import cc.backend.enums.CommentStatusForCompliance;
import cc.backend.manager.service.impl.CommentsServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/17
 */
@RestController
@RequestMapping("/manager/comment")
public class CommentsController {
    @Autowired
    private CommentsServiceImpl commentsService;

    /**
     * @description TODO 获取评论状态选择器
     *
     * @return: cc.backend.common.Result
     */
    @GetMapping("/getCommentStatusForComplianceOption")
    public Result getCommentStatusForComplianceOption()
    {
        List<CommentStatusForCompliance> statusList = Arrays.asList(CommentStatusForCompliance.values());
        return Result.successCDM(statusList,"帖子评论状态获取成功");
    }

    /**
     * @description TODO 通过条件查询帖子数据
     * @param commentsSearchData
     * @param startTime
     * @param endTime
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getComments")
    public Result getComments(@RequestBody SearchData<Comments> commentsSearchData,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
    {
        IPage<Comments> comments = commentsService.getComments(commentsSearchData, startTime, endTime);
        return Result.successCDM(comments,"获取帖子评论成功");
    }


    /**
     * @description TODO 使评论合规操作
     * @param commentId
     * @return: cc.backend.common.Result
     */
    @PostMapping("/complianceComment")
    public Result complianceComment(@RequestParam("commentId")Integer commentId)
    {
        boolean isCompliance = commentsService.updateCommentStatusForCompliance(commentId, CommentStatusForCompliance.COMPLIANCE);
        if (isCompliance)
        {
            return Result.successCM("合规操作成功");
        }
        return Result.error("合规操作失败");
    }

    /**
     * @description TODO 使评论不合规操作
     * @param commentId
     * @return: cc.backend.common.Result
     */
    @PostMapping("/irregularityComment")
    public Result irregularityComment(@RequestParam("commentId")Integer commentId)
    {
        boolean isIrregularity = commentsService.updateCommentStatusForCompliance(commentId, CommentStatusForCompliance.IRREGULARITY);
        if (isIrregularity)
        {
            return Result.successCM("不合规操作成功");
        }
        return Result.error("不合规操作失败");
    }

    @GetMapping("/commentView")
    public Result commentView(@RequestParam("commentId")Integer commentId)
    {
        Comments commentView = commentsService.getOneComment(commentId);
        return Result.successCDM(commentView,"获取单个评论视图成功");
    }
}
