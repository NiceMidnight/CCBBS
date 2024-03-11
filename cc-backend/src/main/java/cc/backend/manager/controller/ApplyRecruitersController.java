package cc.backend.manager.controller;

import cc.backend.common.Result;
import cc.backend.entity.SearchData;
import cc.backend.entity.forrecruiter.ApplyRecruiter;
import cc.backend.enums.ApplyStatus;
import cc.backend.manager.service.impl.ApplyRecruitersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/9
 */
@RestController
@RequestMapping("/manager/applyRecruiter")
public class ApplyRecruitersController {
    @Autowired
    private ApplyRecruitersServiceImpl applyRecruitersService;
    /**
     * TODO 获取招聘权限申请信息成功
     */
    @PostMapping("/getApplyRecruiterMessage")
    public Result getApplyRecruiterMessage(@RequestBody SearchData<ApplyRecruiter> searchData,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
    {
        SearchData<ApplyRecruiter> applyRecruiterMessage = applyRecruitersService.getApplyRecruiterMessage(searchData,startTime,endTime);
        return Result.successCDM(applyRecruiterMessage,"获取招聘权限申请信息成功");
    }
    /**
     * TODO 获取申请状态
     */
    @GetMapping("/getApplyStatusOptions")
    public Result getApplyStatusOptions()
    {
        List<ApplyStatus> list = Arrays.asList(ApplyStatus.values());
        return Result.successCDM(list,"获取申请状态成功");
    }
    /**
     * TODO 获取申请信息
     */
    @GetMapping("/getOneApplyMessage")
    public Result getOneApplyMessage(@RequestParam("applyId")Integer applyId)
    {
        ApplyRecruiter oneApplyMessage = applyRecruitersService.getOneApplyMessage(applyId);
        return Result.successCDM(oneApplyMessage,"");
    }

    @GetMapping("/complianceApply")
    public Result complianceApply(@RequestParam("applyId")Integer applyId,
                                  @RequestParam("userId")Integer userId,
                                  @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isUpdated = applyRecruitersService.updateApplyStatus(applyId, userId, ApplyStatus.COMPLIANCE,tokenInfo);
        if (isUpdated)
        {
            return Result.successCM("更新状态成功");
        }
        return Result.error("更新状态失败");
    }

    @GetMapping("/irregularityApply")
    public Result irregularityApply(@RequestParam("applyId")Integer applyId,
                                    @RequestParam("userId")Integer userId,
                                    @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isUpdated = applyRecruitersService.updateApplyStatus(applyId, userId, ApplyStatus.IRREGULARITY,tokenInfo);
        if (isUpdated)
        {
            return Result.successCM("更新状态成功");
        }
        return Result.error("更新状态失败");
    }
    /**
     * TODO 更新备注信息
     */
    @GetMapping("/updateApplyNotes")
    public Result updateApplyNotes(@RequestParam("applyId")Integer applyId,@RequestParam("notes")String notes)
    {
        applyRecruitersService.updateApplyNotes(applyId,notes);
        return Result.successCM("更新申请"+applyId+"（id）备注成功");
    }
}
