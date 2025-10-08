package com.codenlog.ticket.member.config;

import com.codenlog.ticket.common.interceptor.LogInterceptor;
import com.codenlog.ticket.common.interceptor.MemberInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/08/11:54 PM
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    private MemberInterceptor memberInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);

        registry.addInterceptor(memberInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/member/test",
                        "/member/member/login",
                        "/member/captcha/graph",
                        "/member/captcha/need-graph",
                        "/member/captcha/phone",
                        "/member/captcha/verify-phone"
                );

    }

}
