package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.forrecruiter.ApplyRecruiter;
import cc.backend.user.service.impl.ApplyRecruiterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 申请招聘权限
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@RestController
@RequestMapping("/user/applyRecruiter")
public class ApplyRecruiterController {
    @Autowired
    private ApplyRecruiterServiceImpl applyRecruiterService;
    @RequestMapping("/addAApplyRecruiter")
    public Result addAApplyRecruiter(@RequestBody ApplyRecruiter applyRecruiter, @RequestHeader("Authorization")String tokenInfo)
    {
        boolean isAdd = applyRecruiterService.addAApplyRecruiter(applyRecruiter, tokenInfo);
        if (isAdd)
        {
            return Result.successCM("发起申请成功，将在七日内处理");
        }
        return Result.error("发起申请失败");
    }
}
