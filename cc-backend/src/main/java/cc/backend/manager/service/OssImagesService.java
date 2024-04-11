package cc.backend.manager.service;

import cc.backend.entity.OssImages;
import cc.backend.entity.SearchData;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/19
 */
public interface OssImagesService {
    boolean insertOssImagesData(OssImages ossImages);
    SearchData<OssImages> getOssImages(SearchData<OssImages> searchData, LocalDateTime startTime, LocalDateTime endTime);
    OssImages getOneOssImage(Integer imgId);
    boolean deleteOssImageById(Integer imgId);
}
