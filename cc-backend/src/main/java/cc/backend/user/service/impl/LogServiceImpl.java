package cc.backend.user.service.impl;

import cc.backend.entity.DailyUniqueVisitors;
import cc.backend.user.mapper.LogMapper;
import cc.backend.user.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description 日活跃用户日志
 * @Author Tiamo_null
 * @Date 2024/3/18
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public boolean ActiveOrNot(Integer userId) {
        return logMapper.countByUserIdAndDate(userId) > 0;
    }

    @Override
    public boolean insertDailyUniqueVisitorsLog(DailyUniqueVisitors dailyUniqueVisitors) {
        return logMapper.insert(dailyUniqueVisitors) > 0;
    }
}
