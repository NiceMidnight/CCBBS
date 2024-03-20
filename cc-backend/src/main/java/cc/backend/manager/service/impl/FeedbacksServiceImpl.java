package cc.backend.manager.service.impl;

import cc.backend.entity.feedback.Feedback;
import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.Job;
import cc.backend.enums.FeedbackStatus;
import cc.backend.manager.mapper.FeedbacksMapper;
import cc.backend.manager.service.FeedbacksService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/15
 */
@Service
public class FeedbacksServiceImpl implements FeedbacksService {
    @Autowired
    private FeedbacksMapper feedbacksMapper;

    @Override
    public SearchData<Feedback> getAllFeedback(SearchData<Feedback> searchData, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<Feedback> iPage = new Page<>(searchData.getPageNum(), searchData.getPageSize());
        feedbacksMapper.selectAllFeedbackMessage(iPage,searchData.getData(),startTime,endTime);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public boolean updateFeedbackStatus(Integer feedbackId, String feedbackStatus, boolean upOrDown) {
        try {
            FeedbackStatus status = FeedbackStatus.valueOf(feedbackStatus); // 将字符串转换为枚举值
            if (upOrDown)
            {
                if (status == FeedbackStatus.Pending) {
                    return feedbacksMapper.updateFeedbackStatus(feedbackId,FeedbackStatus.InProgress) > 0;
                } else if (status == FeedbackStatus.InProgress) {
                    return feedbacksMapper.updateFeedbackStatus(feedbackId,FeedbackStatus.Processed) > 0;
                } else if (status == FeedbackStatus.Processed) {
                    return feedbacksMapper.updateFeedbackStatus(feedbackId,FeedbackStatus.Closed) > 0;
                } else return status != FeedbackStatus.Closed;
            }
            else
            {
                if (status == FeedbackStatus.Closed) {
                    return feedbacksMapper.updateFeedbackStatus(feedbackId, FeedbackStatus.Processed) > 0;
                } else if (status == FeedbackStatus.Processed) {
                    return feedbacksMapper.updateFeedbackStatus(feedbackId, FeedbackStatus.InProgress) > 0;
                } else if (status == FeedbackStatus.InProgress) {
                    return feedbacksMapper.updateFeedbackStatus(feedbackId, FeedbackStatus.Pending) > 0;
                } else return status != FeedbackStatus.Pending;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

}
