package cc.backend.manager.controller;


import cc.backend.common.Result;
import cc.backend.entity.Dict;
import cc.backend.entity.SearchData;
import cc.backend.entity.SysImageResource;
import cc.backend.manager.service.ImagesService;
import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * @description TODO 获取系统图片资源
     * @param searchData
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getAllImages")
    public Result getAllImages(@RequestBody SearchData<SysImageResource> searchData) {
        SearchData<SysImageResource> allImages = imagesService.getAllImages(searchData);
        return Result.successCDM(allImages,"查询图片资源成功");
    }

    /**
     * @description TODO 设置图片可见
     * @param id
     * @return: cc.backend.common.Result
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
     * @description TODO 设置图片不可见
     * @param id
     * @return: cc.backend.common.Result
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
     * @description TODO 获取图片选择器内容
     * @return: cc.backend.common.Result
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
     * @description TODO 上传图片
     * @param file
     * @param formData
     * @return: cc.backend.common.Result
     */
    @Transactional
    @PostMapping("/uploadSysImg")
    public Result uploadSysImg(@RequestParam("file") MultipartFile file,
                               @RequestParam("formData") String formData,
                               @RequestHeader("Authorization") String tokenInfo)
    {
        try
        {
            // 构建上传目录的绝对路径
            String absolutePath = uploadPath;
            // 确保目录存在，如果不存在，则创建
            if (!StringUtils.isEmpty(absolutePath))
            {
                File uploadDir = new File(absolutePath);
                if (!uploadDir.exists())
                {
                    System.out.println("上传目录不存在");
                    return Result.error("无法获取上传目录路径");
                }
                SysImageResource sysImageResource = new ObjectMapper().readValue(formData, SysImageResource.class);
                // 构建文件路径
                String filePath = absolutePath + File.separator + sysImageResource.getImgPath();
                // 复制文件到目标路径
                Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                System.out.println(sysImageResource);
                boolean isAddSysImg = imagesService.addSysImgData(sysImageResource, tokenInfo);
                if (isAddSysImg)
                {
                    return Result.successCDM(filePath, "图片上传成功");
                } else return Result.error("图片上传失败");
            } else
            {
                System.out.println("无法获取上传目录路径，图片上传失败");
                return Result.error("图片上传失败");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return Result.error("图片上传失败");
        }
    }
//    @Transactional
//    @PostMapping("/uploadSysImg")
//    public Result uploadSysImg(@RequestParam("file") MultipartFile file,
//                               @RequestParam("formData") String formData,
//                               @RequestHeader("Authorization") String tokenInfo)
//    {
//        try
//        {
//            // 构建上传目录的绝对路径
//            String absolutePath = new File(uploadPath).getAbsolutePath();
//            // 确保目录存在，如果不存在，则创建
//            if (!StringUtils.isEmpty(absolutePath))
//            {
//                File uploadDir = new File(absolutePath);
//                if (!uploadDir.exists())
//                {
//                    System.out.println("上传目录不存在");
//                    return Result.error("无法获取上传目录路径");
//                }
//                SysImageResource sysImageResource = new ObjectMapper().readValue(formData, SysImageResource.class);
//                // 构建文件路径
//                String filePath = absolutePath + File.separator + "sys_img" + File.separator + sysImageResource.getImgPath();
//                // 复制文件到目标路径
//                Files.copy(file.getInputStream(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
//                System.out.println(sysImageResource);
//                boolean isAddSysImg = imagesService.addSysImgData(sysImageResource,tokenInfo);
//                if (isAddSysImg)
//                {
//                    return Result.successCDM(filePath,"图片上传成功");
//                }else return Result.error("图片上传失败");
//            } else
//            {
//                System.out.println("无法获取上传目录路径，图片上传失败");
//                return Result.error("图片上传失败");
//            }
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//            return Result.error("图片上传失败");
//        }
//    }

    /**
     * @description TODO 删除系统图片
     * @param imgId
     * @return: cc.backend.common.Result
     */
    @Transactional
    @GetMapping("/deleteSysImgResourceById")
    public Result deleteSysImgResourceById(@RequestParam("imgId")Integer imgId)
    {
        try
        {
            String imgPath = imagesService.getSysImgResourceById(imgId).getImgPath();
            if (imgPath != null)
            {
                // 从imgPath中提取文件名
                String fileName = Paths.get(imgPath).getFileName().toString();
                // 删除本地文件
                String absolutePath = new File(uploadPath).getAbsolutePath();
                String filePath = absolutePath + File.separator + "sys_img" + File.separator + fileName;
                Files.deleteIfExists(Paths.get(filePath));
                //删除数据库数据
                boolean isDeletedImage = imagesService.deleteSysImgResourceById(imgId);
                if (isDeletedImage) {
                    return Result.successCM("图片"+imgId+"删除成功");
                } return Result.error("删除图片数据库数据失败");
            }
            return Result.successCM("获取不到"+imgId+"路径名称");
        } catch (Exception e)
        {
            e.printStackTrace();
            return Result.error("图片删除失败："+e);
        }
    }

    /**
     * @description TODO 更新数据库图片数据
     * @param editSysImgData
     * @return: cc.backend.common.Result
     */
    @PostMapping("updateSysImageResource")
    public Result updateSysImageResource(@RequestBody SysImageResource editSysImgData)
    {
        boolean isUpdated = imagesService.updateSysImageResource(editSysImgData);
        if (isUpdated)
        {
            return Result.successCM("更新图片"+editSysImgData.getId()+"成功");
        }
        return Result.error("更新图片"+editSysImgData.getId()+"失败");
    }
}
