package com.tiv.api.config;

import com.tiv.api.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // 显式定义bean
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/login/code");
    }
}
