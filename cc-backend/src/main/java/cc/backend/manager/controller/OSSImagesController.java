package cc.backend.manager.controller;

import cc.backend.common.AliyunOSSService;
import cc.backend.common.Result;
import cc.backend.entity.OssImages;
import cc.backend.entity.Post;
import cc.backend.entity.SearchData;
import cc.backend.manager.service.impl.OssImagesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @Description 云端图片管理
 * @Author Tiamo_null
 * @Date 2024/2/19
 */
@RestController
@RequestMapping("/manager/ossImages")
public class OSSImagesController {
    @Autowired
    private OssImagesServiceImpl ossImagesService;
    @Autowired
    private AliyunOSSService aliyunOSSService;
    @RequestMapping("/getOssImages")
    public Result getOssImages(@RequestBody SearchData<OssImages> searchData,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
    {
        SearchData<OssImages> ossImages = ossImagesService.getOssImages(searchData, startTime, endTime);
        return Result.successCDM(ossImages,"获取云端资源");
    }
    @GetMapping("/deleteOssImageById")
    public Result deleteOssImageById(@RequestParam("imgId")Integer imgId)
    {
        OssImages oneOssImage = ossImagesService.getOneOssImage(imgId);
        if (oneOssImage != null)
        {
            boolean isDeleteFromOss = aliyunOSSService.deleteOssImageByFileName(oneOssImage.getImgName());
            if (isDeleteFromOss)
            {
                boolean isDeleteFromSql = ossImagesService.deleteOssImageById(imgId);
                if (isDeleteFromSql) return Result.successCM("删除云端图片成功");
                else return Result.successCM("删除云端图片成功，但操作数据库失败");
            }
        }
        return Result.error("删除云端图片失败");
    }
}
