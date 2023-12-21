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
    private List<SysImageResource> setIndexImg() {
        List<SysImageResource> sysImageResources = indexImgMapper.selectList(null);
        return sysImageResources;
    }

    @Override
    public List<String> getIndexImage() {
        List<SysImageResource> sysImageResources = indexImgMapper.selectList(null);
        List<String> imgPaths = sysImageResources.stream()
                .map(SysImageResource::getImgPath)
                .collect(Collectors.toList());
        System.out.println(imgPaths);
        return imgPaths;
    }
}
