package cc.backend.manager.service.impl;

import cc.backend.entity.Images;
import cc.backend.manager.mapper.OssImagesMapper;
import cc.backend.manager.service.OssImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/19
 */
@Service
public class OssImagesServiceImpl implements OssImagesService {
    @Autowired
    private OssImagesMapper ossImagesMapper;
    /**
     * @description TODO 插入图片
     * @param images
     * @return: boolean
     */
    @Override
    public boolean insertOssImagesData(Images images) {
        return ossImagesMapper.insert(images) > 0;
    }
}
