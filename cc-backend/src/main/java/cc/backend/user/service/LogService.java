package cc.backend.user.service;

import cc.backend.entity.DailyUniqueVisitors;

/**
 * @Description 日志
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
public interface LogService {
    boolean ActiveOrNot(Integer userId);

    boolean insertDailyUniqueVisitorsLog(DailyUniqueVisitors dailyUniqueVisitors);
}
