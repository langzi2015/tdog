package com.lirb.tdog.config;

import com.lirb.tdog.interceptor.LoginInterceptor;
import com.lirb.tdog.interceptor.OtherInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public OtherInterceptor getOtherInterceptor() {
        return new OtherInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(getOtherInterceptor())
                .addPathPatterns("/**");
    }
}
