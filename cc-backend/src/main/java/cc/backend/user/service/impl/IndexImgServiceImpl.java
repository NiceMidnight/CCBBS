package cc.backend.user.service.impl;


import cc.backend.entity.SysImageResource;
import cc.backend.user.mapper.IndexImgMapper;
import cc.backend.user.service.IndexImgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @FileName IndexImgServiceImpl
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-23
 **/
@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Autowired
    private IndexImgMapper indexImgMapper;

    /**
     * @description TODO 获取走马灯图片路径
     *
     * @return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> getIndexImage() {
        QueryWrapper<SysImageResource> sysImageResourceQueryWrapper = new QueryWrapper<>();
        sysImageResourceQueryWrapper.eq("type",0);
        List<SysImageResource> sysImageResources = indexImgMapper.selectList(sysImageResourceQueryWrapper);
        List<String> imgPaths = sysImageResources.stream()
                .map(SysImageResource::getImgPath)
                .collect(Collectors.toList());
        System.out.println(imgPaths);
        return imgPaths;
    }
}
