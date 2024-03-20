package cc.backend.manager.service;

import cc.backend.entity.feedback.Feedback;
import cc.backend.entity.SearchData;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/15
 */
public interface FeedbacksService {
    SearchData<Feedback> getAllFeedback(SearchData<Feedback> searchData, LocalDateTime startTime,LocalDateTime endTime);

    boolean updateFeedbackStatus(Integer feedbackId,String feedbackStatus,boolean upOrDown);
}
