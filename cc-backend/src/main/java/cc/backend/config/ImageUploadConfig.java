package cc.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 配置静态资源映射
 * @Author Tiamo_null
 * @Date 2024/2/2
 */
@Configuration
public class ImageUploadConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler("/static/sys_img/**").addResourceLocations("file:E:\\CampusCommunity\\cc-backend\\src\\main\\resources\\static\\sys_img").addResourceLocations("file:E:\\CampusCommunity\\cc-backend\\src\\main\\resources\\static\\sys_img\\adminHead");
    }
}
