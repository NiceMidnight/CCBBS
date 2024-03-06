package cc.backend.user.service;


import cc.backend.entity.forrecruiter.ApplyRecruiter;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/3/5
 */

public interface ApplyRecruiterService {
    boolean addAApplyRecruiter(ApplyRecruiter applyRecruiter,String tokenInfo);
}
