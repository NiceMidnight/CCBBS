package cc.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author Tiamo_null
 * @Date 2023/11/7
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/manager/images/uploadSysImg")
                .excludePathPatterns("/manager/login")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/verify")
                .excludePathPatterns("/user/getUserInfo")
                .excludePathPatterns("/user/logout")
                .excludePathPatterns("/user/resources/**"); // 拦截？放行
    }
}
