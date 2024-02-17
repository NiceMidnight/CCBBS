package cc.backend.user.controller;

import cc.backend.common.AliyunOSSService;
import cc.backend.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/2/16
 */
@RestController
@RequestMapping("/user/images")
public class PostImageController {
    @Autowired
    private AliyunOSSService aliyunOSSService;
    /**
     * @description TODO 富文本编辑器图片上传阿里云OSS并进行回显
     * @param file
     * @return: cc.backend.common.Result
     */
    @RequestMapping(value = "/upload")
    public Result uploadImages(@RequestParam("file") MultipartFile file) throws IOException
    {
        String imgUrl = aliyunOSSService.uploadPostFile(file);
        if (imgUrl != null)
        {
            return Result.successCDM(imgUrl,"上传成功");
        }
        return Result.error("上传失败");
    }
}
