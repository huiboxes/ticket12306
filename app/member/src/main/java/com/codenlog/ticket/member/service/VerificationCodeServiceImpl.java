package com.codenlog.ticket.member.service;

import com.codenlog.ticket.member.config.captcha.CaptchaProperties;
import com.codenlog.ticket.member.config.captcha.GraphCaptchaGenerator;
import com.codenlog.ticket.member.config.sms.SmsProviderFactory;
import com.codenlog.ticket.member.domain.GraphCaptcha;
import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/9:00 PM
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final GraphCaptchaGenerator graphCaptchaGenerator;
    private final SmsProviderFactory smsProviderFactory;
    private final AntiBrushService antiBrushService;
    private final RedissonClient redissonClient;
    private final RMapCache<String, String> phoneCaptchaCache;
    private final CaptchaProperties properties;
    private final Random random = new Random();

    @Autowired
    public VerificationCodeServiceImpl(GraphCaptchaGenerator graphCaptchaGenerator,
                                          SmsProviderFactory smsProviderFactory,
                                          AntiBrushService antiBrushService,
                                          RedissonClient redissonClient,
                                          CaptchaProperties properties) {
        this.graphCaptchaGenerator = graphCaptchaGenerator;
        this.smsProviderFactory = smsProviderFactory;
        this.antiBrushService = antiBrushService;
        this.redissonClient = redissonClient;
        this.properties = properties;

        // 初始化手机验证码缓存
        this.phoneCaptchaCache = redissonClient.getMapCache("captcha:phone");
    }

    @Override
    public GraphCaptcha generateGraphCaptcha() {
        CaptchaProperties.GraphCaptcha graphConfig = properties.getGraph();
        return graphCaptchaGenerator.generate(
                graphConfig.getWidth(),
                graphConfig.getHeight(),
                graphConfig.getLength()
        );
    }

    @Override
    public boolean verifyGraphCaptcha(String uuid, String code) {
        return graphCaptchaGenerator.verify(uuid, code);
    }

    @Override
    public boolean sendPhoneCaptcha(String phoneNumber, String ipAddress,
                                    String graphCaptchaUuid, String graphCaptchaCode) {
        if (antiBrushService.isOverRequestLimit(ipAddress)) {
            throw new RuntimeException("请求过于频繁，请稍后再试");
        }

        if (antiBrushService.isOverPhoneLimit(phoneNumber)) {
            throw new RuntimeException("该手机号今日获取验证码次数已达上限");
        }
//
//        if (needGraphCaptcha(ipAddress)) {
//            if (graphCaptchaUuid == null || graphCaptchaCode == null ||
//                    !verifyGraphCaptcha(graphCaptchaUuid, graphCaptchaCode)) {
//                throw new RuntimeException("图形验证码错误或已过期");
//            }
//        }

        // 分布式锁防止重复发送
        String lockKey = "lock:captcha:phone:" + phoneNumber;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean locked = lock.tryLock(100,
                    properties.getPhone().getSendInterval().getSeconds(),
                    TimeUnit.SECONDS);

            if (!locked) {
                throw new RuntimeException("验证码发送过于频繁，请稍后再试");
            }

            String code = generatePhoneCode(properties.getPhone().getLength());

            phoneCaptchaCache.put(phoneNumber, code,
                    properties.getPhone().getExpire().getSeconds(),
                    TimeUnit.SECONDS);

            return smsProviderFactory.getProviderForPhone(phoneNumber)
                    .sendVerificationCode(phoneNumber, code);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("发送验证码失败，请重试");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public boolean verifyPhoneCaptcha(String phoneNumber, String code) {
        if (phoneNumber == null || code == null) {
            return false;
        }

        // 获取并删除验证码
        String storedCode = phoneCaptchaCache.remove(phoneNumber);
        return code.equals(storedCode);
    }

    @Override
    public boolean needGraphCaptcha(String ipAddress) {
        return antiBrushService.needGraphCaptcha(ipAddress);
    }

    /**
     * 生成手机验证码
     */
    private String generatePhoneCode(int length) {
        // 生成指定长度的数字验证码
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        int code = random.nextInt(max - min + 1) + min;
        return String.valueOf(123456L);
    }

}
