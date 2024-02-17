package cc.backend.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 阿里云oss存储文件
 * @Author Tiamo_null
 * @Date 2024/2/16
 */
@Service
public class AliyunOSSService {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint ;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId ;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    public String uploadPostFile(MultipartFile file) throws IOException {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 创建文件元信息，指定上传的文件类型
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            // 上传文件到阿里云 OSS
            ossClient.putObject(bucketName, file.getOriginalFilename(), inputStream, metadata);
            // 构建图片的访问 URL

            return "https://" + bucketName + "." + endpoint + "/" + file.getOriginalFilename();
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }
    }
}
