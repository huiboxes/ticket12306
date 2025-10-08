package com.codenlog.ticket.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.codenlog.ticket.common.context.LoginMemberContext;
import com.codenlog.ticket.common.response.MemberLoginResponse;
import com.codenlog.ticket.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/08/11:51 PM
 */
@Component
public class MemberInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MemberInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取header的token参数
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        if (StrUtil.isNotBlank(token)) {
            LOG.info("获取会员登录token：{}", token);
            JSONObject loginMember = JwtUtil.getJSONObject(token);
            LOG.info("当前登录会员：{}", loginMember);
            MemberLoginResponse member = JSONUtil.toBean(loginMember, MemberLoginResponse.class);
            LoginMemberContext.setMember(member);
        }
        return true;
    }

}