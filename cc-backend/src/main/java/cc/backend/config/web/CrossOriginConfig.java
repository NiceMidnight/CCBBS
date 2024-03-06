package cc.backend.config.web;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @FileName CrossOriginConfig
 * @Description cross-domain 跨域问题全局配置
 * @Author Tiamo_Null
 * @date 2023-07-29
 **/
@Configuration
public class CrossOriginConfig {
    //跨域请求有效时常默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //设置访问源地址
        corsConfiguration.addAllowedHeader("*");    //设置访问源请求头
        corsConfiguration.addAllowedMethod("*");    //设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**",corsConfiguration);  //对接口配置跨域设置
        return new CorsFilter(source);
    }


}
