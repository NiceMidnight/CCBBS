package cc.backend.manager.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.ApplyRecruiter;
import cc.backend.enums.ApplyStatus;
import cc.backend.manager.mapper.ApplyRecruitersMapper;
import cc.backend.manager.service.ApplyRecruitersService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/9
 */
@Service
public class ApplyRecruitersServiceImpl implements ApplyRecruitersService {


    @Autowired
    private ApplyRecruitersMapper applyRecruitersMapper;
    @Autowired
    private Token token;
    @Autowired
    private UsersServiceImpl usersService;
    /**
     * TODO 获取全部申请信息
     */
    @Override
    public SearchData<ApplyRecruiter> getApplyRecruiterMessage(SearchData<ApplyRecruiter> searchData, LocalDateTime startTime, LocalDateTime endTime) {
        IPage<ApplyRecruiter> iPage = new Page<>(searchData.getPageNum(), searchData.getPageSize());
        applyRecruitersMapper.selectAll(iPage,searchData.getData(),startTime,endTime);
        return SearchData.pageData((int) iPage.getCurrent(), (int) iPage.getSize(), (int) iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public ApplyRecruiter getOneApplyMessage(Integer applyId) {
        return applyRecruitersMapper.selectById(applyId);
    }

    /**
     * TODO 更改申请状态--根据不同状态更改用户角色
     */
    @Override
    public boolean updateApplyStatus(Integer applyId, Integer userId,ApplyStatus applyStatus,String tokenInfo) {
        int handlingUserId = token.getUserId(tokenInfo);
        if (applyStatus == ApplyStatus.COMPLIANCE)
        {
            boolean isUpdateUserRole = usersService.updateUserRole(userId, 2);
            if (isUpdateUserRole)
            {
                int updateApplyStatusById = applyRecruitersMapper.updateApplyStatusById(applyId, handlingUserId, applyStatus, new Date());
                if (updateApplyStatusById <= 0) {
                    usersService.updateUserRole(userId, 0);
                    return false;
                }
                return true;
            }
        }
        else if (applyStatus == ApplyStatus.IRREGULARITY)
        {
            boolean isUpdateUserRole = usersService.updateUserRole(userId, 0);
            if (isUpdateUserRole)
            {
                int updateApplyStatusById = applyRecruitersMapper.updateApplyStatusById(applyId, handlingUserId, applyStatus, new Date());
                if (updateApplyStatusById <= 0) {
                    usersService.updateUserRole(userId, 2);
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    /**
     * TODO 更新备注信息
     */
    @Override
    public boolean updateApplyNotes(Integer applyId, String notes) {
        ApplyRecruiter applyRecruiter = applyRecruitersMapper.selectById(applyId);
        applyRecruiter.setNotes(notes);
        UpdateWrapper<ApplyRecruiter> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("apply_id",applyId);
        return applyRecruitersMapper.update(applyRecruiter,updateWrapper) > 0;
    }
}
