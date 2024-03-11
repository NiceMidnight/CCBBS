package cc.backend.user.service.impl;

import cc.backend.common.token.Token;
import cc.backend.entity.forrecruiter.ApplyRecruiter;
import cc.backend.enums.ApplyStatus;
import cc.backend.user.mapper.ApplyRecruiterMapper;
import cc.backend.user.service.ApplyRecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */
@Service
public class ApplyRecruiterServiceImpl implements ApplyRecruiterService {
    @Autowired
    private Token token;
    @Autowired
    private ApplyRecruiterMapper applyRecruiterMapper;
    /**
     * TODO 申请权限
     */
    @Override
    public boolean addAApplyRecruiter(ApplyRecruiter applyRecruiter, String tokenInfo) {
        int userId = token.getUserId(tokenInfo);
        applyRecruiter.setUserId(userId);
        applyRecruiter.setCreatedAt(new Date());
        return applyRecruiterMapper.insert(applyRecruiter) > 0;
    }

}
