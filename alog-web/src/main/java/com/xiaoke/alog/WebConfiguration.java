package com.xiaoke.alog;

import com.xiaoke.alog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 初始化登录拦截器
     */
    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")// 请求拦截
                .excludePathPatterns("/user/register", "/user/login");// 不拦截注册、登录请求
    }

    /**
     * 配置跨域访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 添加映射路径
                .allowedOrigins("*") // 放行哪些原始域
                .allowCredentials(true) // 是否发送Cookie信息
                .allowedMethods("*") // 放行哪些原始域(请求方式)
                .allowedHeaders("*"); // 放行哪些原始域(头部信息)
    }
}
