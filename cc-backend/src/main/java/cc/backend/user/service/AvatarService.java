package cc.backend.user.service;

import cc.backend.entity.SysImageResource;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/4/9
 */
public interface AvatarService {
    boolean addSysImgData(SysImageResource addSysImgData, Integer userId);
}
