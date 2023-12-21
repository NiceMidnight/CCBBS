package cc.backend.manager.controller;


import cc.backend.commom.Result;
import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;
import cc.backend.entity.SysImageResource;
import cc.backend.manager.service.ImagesService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * @FileName ImagesController
 * @Description
 * @Author Tiamo_Null
 * @date 2023-08-21
 **/
@RestController
@RequestMapping("/manager/images")
public class ImagesController {
    @Autowired
    private ImagesService imagesService;
    /**
     * TODO 获取系统图片资源
     */
    @PostMapping("/getAllImages")
    public Result getAllImages(@RequestBody SearchData<SysImageResource> searchData) {
        SearchData allImages = imagesService.getAllImages(searchData);
        return Result.successCDM(allImages,"查询图片资源成功");
    }

    /**
     * TODO 设置图片可见
     */
    @RequestMapping("/visible")
    public Result visible(Integer id) {
        boolean visible = imagesService.visible(id);
        if (visible) {
            return Result.successCDM(id,"可见操作成功");
        }
        return Result.error("图片可见操作失败");
    }

    /**
     * TODO 设置图片不可见
     */
    @RequestMapping("/inVisible")
    public Result inVisible(Integer id) {
        boolean disVisible = imagesService.disVisible(id);
        if (disVisible) {
            return Result.successCDM(id,"不可见操作成功");
        }
        return Result.error("不可见操作失败");
    }
    /**
     * TODO 获取图片选择器内容
     */
    @GetMapping("/getSysImgOption")
    public Result getSysImgOption() {
        List<Dict> sysImgOption = imagesService.getSysImgOption();
        return Result.successCDM(sysImgOption,"获取图片选择器内容成功");
    }

    /**
     * TODO 本地路径
     */
    @Value("${upload.path}")
    private String uploadPath;
    /**
     * TODO 上传图片到本地路径
     */
    @PostMapping("/uploadSysImg")
    public Result uploadSysImg(@RequestParam("file") MultipartFile file){
        try {
            // 构建上传目录的绝对路径
            String absolutePath = new File(uploadPath).getAbsolutePath();

            // 确保目录存在，如果不存在，则创建
            if (!StringUtils.isEmpty(absolutePath)) {
                File uploadDir = new File(absolutePath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                // 构建文件路径
                String filePath = absolutePath + File.separator + "sys_img" + File.separator + file.getOriginalFilename();

                // 复制文件到目标路径
                Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
                return Result.successCDM(filePath,"图片上传成功");
            } else {
                System.out.println("无法获取上传目录路径");
                return Result.error("图片上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("图片上传失败");
        }
    }
    /**
     * TODO 添加系统图片信息
     */
    @PostMapping("addSysImgData")
    public Result addSysImgData(@RequestBody SysImageResource addSysImgData,@RequestHeader("Authorization") String tokenInfo) {
        boolean isAddSysImg = imagesService.addSysImgData(addSysImgData, tokenInfo);
        if (isAddSysImg) {
            return Result.successCM("添加成功");
        }else return Result.error("添加失败");
    }

}
