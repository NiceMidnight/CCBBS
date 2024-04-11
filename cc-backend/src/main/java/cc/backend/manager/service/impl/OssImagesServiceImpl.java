package cc.backend.manager.service.impl;

import cc.backend.entity.OssImages;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.enums.ImageStatus;
import cc.backend.manager.mapper.OssImagesMapper;
import cc.backend.manager.service.OssImagesService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
     * TODO 插入图片
     */
    @Override
    public boolean insertOssImagesData(OssImages ossImages) {
        return ossImagesMapper.insert(ossImages) > 0;
    }

    /**
     * TODO 查询云端图片资源
     */
    @Override
    public SearchData<OssImages> getOssImages(SearchData<OssImages> searchData, LocalDateTime startTime, LocalDateTime endTime) {
        Page<OssImages> ossImagesPage = new Page<>(searchData.getPageNum(), searchData.getPageSize());
        ossImagesMapper.selectOssImages(ossImagesPage, searchData.getData(),startTime,endTime, ImageStatus.legitimate);
        return SearchData.pageData(
                (int) ossImagesPage.getCurrent(),
                (int) ossImagesPage.getSize(),
                (int) ossImagesPage.getTotal(),
                ossImagesPage.getRecords());
    }

    @Override
    public OssImages getOneOssImage(Integer imgId) {
        return ossImagesMapper.selectById(imgId);
    }

    @Override
    public boolean deleteOssImageById(Integer imgId) {
        return ossImagesMapper.updateOssImageStatus(imgId,ImageStatus.illegality) > 0;
    }

}
