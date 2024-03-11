package cc.backend.manager.service;

import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.ApplyRecruiter;
import cc.backend.enums.ApplyStatus;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/9
 */
public interface ApplyRecruitersService {
    SearchData<ApplyRecruiter> getApplyRecruiterMessage(SearchData<ApplyRecruiter> searchData, LocalDateTime startTime, LocalDateTime endTime);

    ApplyRecruiter getOneApplyMessage(Integer applyId);

    boolean updateApplyStatus(Integer applyId, Integer userId,ApplyStatus applyStatus,String tokenInfo);

    boolean updateApplyNotes(Integer applyId,String notes);
}
