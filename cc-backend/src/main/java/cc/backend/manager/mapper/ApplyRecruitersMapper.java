package cc.backend.manager.mapper;

import cc.backend.entity.forrecruiter.ApplyRecruiter;
import cc.backend.enums.ApplyStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/9
 */
public interface ApplyRecruitersMapper extends BaseMapper<ApplyRecruiter> {
    IPage<ApplyRecruiter> selectAll(IPage<ApplyRecruiter> iPage, ApplyRecruiter applyRecruiter,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime")LocalDateTime endTime);

    int updateApplyStatusById(@Param("applyId")Integer applyId,
                              @Param("handlingUserId")int userId,
                              @Param("applyStatus")ApplyStatus applyStatus,
                              @Param("handlingAt")Date date);
}
