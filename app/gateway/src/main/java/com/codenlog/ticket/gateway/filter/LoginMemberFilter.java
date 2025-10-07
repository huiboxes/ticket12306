package com.codenlog.ticket.gateway.filter;

import com.codenlog.ticket.gateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/07/11:37 PM
 */
@Component
public class LoginMemberFilter implements GlobalFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 排除不需要拦截的请求
        if (path.contains("/admin")
                || path.contains("/member/member/login")
                || path.contains("/member/captcha/graph")
                || path.contains("/member/captcha/need-graph")
                || path.contains("/member/captcha/phone")
                || path.contains("/member/captcha/verify-phone")) {
            LOG.info("不需要登录验证：{}", path);
            return chain.filter(exchange);
        } else {
            LOG.info("需要登录验证：{}", path);
        }
        // 获取header的Authorization参数
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        LOG.info("会员登录验证开始，Authorization header：{}", authorizationHeader);
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            LOG.info( "Authorization header为空，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token;
        if (authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        } else {
            // 如果没有Bearer前缀，就使用整个header值作为token
            token = authorizationHeader;
        }

        // 校验token是否有效，包括token是否被改过，是否过期
        boolean validate = JwtUtil.validate(token);
        if (validate) {
            LOG.info("token有效，放行该请求");
            return chain.filter(exchange);
        } else {
            LOG.warn( "token无效，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

    }

    /**
     * 优先级设置  值越小  优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}