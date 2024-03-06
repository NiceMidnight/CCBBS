package cc.backend.config.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 创建实例
 * @Author Tiamo_null
 * @Date 2024/2/16
 */
@Configuration
public class OssClientConfig {
    @Value("${aliyun.oss.endpoint}")
    String endpoint ;
    @Value("${aliyun.oss.accessKeyId}")
    String accessKeyId ;
    @Value("${aliyun.oss.accessKeySecret}")
    String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    String bucketName;
    @Bean
    public OSSClient createOssClient() {
        return (OSSClient)new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}

