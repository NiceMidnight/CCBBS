package cc.backend.common;

import cc.backend.entity.Images;
import cc.backend.manager.service.impl.OssImagesServiceImpl;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @Description 阿里云oss存储文件
 * @Author Tiamo_null
 * @Date 2024/2/16
 */
@Service
public class AliyunOSSService {
    @Autowired
    private OssImagesServiceImpl ossImagesService;
    @Value("${aliyun.oss.endpoint}")
    private String endpoint ;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId ;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Synchronized
    public String uploadPostFile(MultipartFile file) throws IOException {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 创建文件元信息，指定上传的文件类型
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            // 使用UUID生成唯一的文件名
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();
            // 上传文件到阿里云 OSS
            ossClient.putObject(bucketName, fileName, inputStream, metadata);
            // 构建图片的访问 URL
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            //存储数据库
            Images images = new Images();
            images.setUploadTime(new Date());
            images.setImgName(fileName);
            images.setImgUrl(url);
            ossImagesService.insertOssImagesData(images);

            return url;
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }
    }
}
