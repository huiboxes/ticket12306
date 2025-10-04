package com.codenlog.ticket.member.service;

import com.codenlog.ticket.member.config.captcha.CaptchaProperties;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:26 PM
 */
@Service
public class AntiBrushService {

    private final RedissonClient redissonClient;
    private final int maxIpRequests;
    private final long timeWindowSeconds;
    private final int graphCaptchaThreshold;

    @Autowired
    public AntiBrushService(RedissonClient redissonClient, CaptchaProperties properties) {
        this.redissonClient = redissonClient;
        this.maxIpRequests = properties.getAntiBrush().getMaxIpRequests();
        this.timeWindowSeconds = properties.getAntiBrush().getTimeWindow().getSeconds();
        this.graphCaptchaThreshold = properties.getAntiBrush().getGraphCaptchaThreshold();
    }

    /**
     * 记录请求并检查是否需要图形验证码
     * @param ipAddress 客户端IP
     * @return 是否需要图形验证码
     */
    public boolean needGraphCaptcha(String ipAddress) {
        String counterKey = "captcha:counter:ip:" + ipAddress;

        // 获取计数器
        RAtomicLong counter = redissonClient.getAtomicLong(counterKey);
        long count = counter.incrementAndGet();

        // 设置过期时间（只在第一次设置）
        if (count == 1) {
            counter.expire(timeWindowSeconds, TimeUnit.SECONDS);
        }

        // 超过阈值则需要图形验证码
        return count > graphCaptchaThreshold;
    }

    /**
     * 检查是否超过请求限制
     * @param ipAddress 客户端IP
     * @return 是否超过限制
     */
    public boolean isOverRequestLimit(String ipAddress) {
        String counterKey = "captcha:counter:ip:" + ipAddress;

        // 获取当前计数
        RAtomicLong counter = redissonClient.getAtomicLong(counterKey);
        long count = counter.get();

        // 检查是否超过最大请求数
        return count > maxIpRequests;
    }

    /**
     * 记录手机号请求次数
     * @param phoneNumber 手机号
     * @return 是否超过发送频率限制
     */
    public boolean isOverPhoneLimit(String phoneNumber) {
        String counterKey = "captcha:counter:phone:" + phoneNumber;

        // 获取计数器
        RAtomicLong counter = redissonClient.getAtomicLong(counterKey);
        long count = counter.incrementAndGet();

        // 设置过期时间（只在第一次设置）
        if (count == 1) {
            counter.expire(24, TimeUnit.HOURS); // 24小时内的计数
        }

        // 手机号的日发送上限10条
        return count > 10;
    }
}
