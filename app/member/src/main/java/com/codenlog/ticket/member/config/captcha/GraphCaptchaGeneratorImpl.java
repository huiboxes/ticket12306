package com.codenlog.ticket.member.config.captcha;

import cn.hutool.core.lang.UUID;
import com.codenlog.ticket.common.util.CaptchaImageUtil;
import com.codenlog.ticket.member.domain.GraphCaptcha;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:41 PM
 */
@Component
public class GraphCaptchaGeneratorImpl implements GraphCaptchaGenerator {

    private final RMapCache<String, String> captchaCache;
    
    @Value("${app.captcha.graph.expire}")
    private long expireSeconds;

    @Autowired
    public GraphCaptchaGeneratorImpl(RedissonClient redissonClient) {
        this.captchaCache = redissonClient.getMapCache("captcha:graph");
    }

    @Override
    public GraphCaptcha generate(int width, int height, int length) {
        // 1. 生成随机码
        String code = CaptchaImageUtil.genText(length);
        // 2. 画成图片
        String base64 = CaptchaImageUtil.drawBase64(code, width, height);
        // 3. 生成 uuid
        String uuid = UUID.randomUUID().toString(true); // 无横杠
        // 4. 缓存并设置过期
        captchaCache.put(uuid, code, expireSeconds, TimeUnit.SECONDS);
        // 5. 返回给前端
        long expireTime = System.currentTimeMillis() + expireSeconds * 1000;
        return new GraphCaptcha(uuid, base64, expireTime);
    }

    @Override
    public boolean verify(String uuid, String code) {
        if (uuid == null || code == null) return false;
        String cached = captchaCache.remove(uuid); // 一次性取出并删除
        return cached != null && cached.equalsIgnoreCase(code);
    }
}
