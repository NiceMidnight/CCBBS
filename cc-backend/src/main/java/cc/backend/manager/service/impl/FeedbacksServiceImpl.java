package cc.backend.manager.service.impl;

import cc.backend.entity.Feedback;
import cc.backend.entity.SearchData;
import cc.backend.manager.service.FeedbacksService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/15
 */
@Service
public class FeedbacksServiceImpl implements FeedbacksService {

    @Override
    public SearchData<Feedback> getAllFeedback(SearchData<Feedback> searchData, LocalDateTime startTime, LocalDateTime endTime) {

        return null;
    }
}
