package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.common.token.Token;
import cc.backend.entity.DailyUniqueVisitors;
import cc.backend.user.service.impl.LogServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description 日活跃日志
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
@RestController
@RequestMapping("/user/log")
public class LogController {
    @Autowired
    private LogServiceImpl logService;
    @Autowired
    private Token token;
    @GetMapping("/enterDailyUniqueVisitorsLog")
    public Result enterDailyUniqueVisitorsLog(HttpServletRequest request,
                                              @RequestHeader("Authorization")String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        if (logService.ActiveOrNot(userId))
        {
            return Result.successCM("用户今日已活跃");
        }
        DailyUniqueVisitors dailyUniqueVisitors = new DailyUniqueVisitors();
        dailyUniqueVisitors.setUserId(userId);
        String ipAddress = request.getRemoteAddr();
        dailyUniqueVisitors.setIp(ipAddress);
        dailyUniqueVisitors.setFirstAccessTime(new Date());
        boolean isInsert = logService.insertDailyUniqueVisitorsLog(dailyUniqueVisitors);
        if (isInsert)
        {
            return Result.successCM("录入日活跃数据成功");
        }
        return Result.error("录入日活跃数据失败");
    }

}
