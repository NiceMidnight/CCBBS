package cc.backend.user.controller;

import cc.backend.common.AliyunOSSService;
import cc.backend.common.Result;
import cc.backend.common.token.Token;
import cc.backend.entity.SysImageResource;
import cc.backend.user.service.impl.AvatarServiceImpl;
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
import java.nio.file.StandardCopyOption;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2024/4/9
 */
@RestController
@RequestMapping("/user/avatar")
public class AvatarController {
    @Autowired
    private AliyunOSSService aliyunOSSService;
    /**
     * @description TODO 富文本编辑器图片上传阿里云OSS并进行回显
     * @param file
     * @return: cc.backend.common.Result
     */
    @RequestMapping("/uploadAvatar")
    public Result uploadImages(@RequestParam("file") MultipartFile file) throws IOException
    {
        System.out.println(file);
        String imgUrl = aliyunOSSService.uploadPostFile(file);
        if (imgUrl != null)
        {
            return Result.successCDM(imgUrl,"上传成功");
        }
        return Result.error("上传失败");
    }
}
