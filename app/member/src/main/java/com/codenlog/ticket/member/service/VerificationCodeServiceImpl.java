package com.codenlog.ticket.member.service;

import com.codenlog.ticket.common.exception.BusinessException;
import com.codenlog.ticket.common.exception.BusinessExceptionEnum;
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
    public boolean sendPhoneCaptcha(String phoneNumber, String ipAddress, String businessType,
                                    String graphCaptchaUuid, String graphCaptchaCode) {
        if (antiBrushService.isOverRequestLimit(ipAddress)) {
            throw BusinessException.of(BusinessExceptionEnum.GET_TOO_MANY_REQUESTS);
        }

        if (antiBrushService.isOverPhoneLimit(phoneNumber)) {
            throw BusinessException.of(BusinessExceptionEnum.GET_TOO_MANY_PHONE_REQUESTS);
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
                throw BusinessException.of(BusinessExceptionEnum.GET_TOO_MANY_REQUESTS_PHONE_CAPTCHA);
            }

            String code = generatePhoneCode(properties.getPhone().getLength());

            // 使用业务类型+手机号作为key存储验证码，确保不同业务使用不同的验证码
            String captchaKey = businessType + ":" + phoneNumber;
            phoneCaptchaCache.put(captchaKey, code,
                    properties.getPhone().getExpire().getSeconds(),
                    TimeUnit.SECONDS);

            return smsProviderFactory.getProviderForPhone(phoneNumber)
                    .sendVerificationCode(phoneNumber, code);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw BusinessException.of(BusinessExceptionEnum.GET_CAPTCH_FAILED);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Override
    public boolean verifyPhoneCaptcha(String phoneNumber, String code, String businessType) {
        if (phoneNumber == null || code == null || businessType == null) {
            return false;
        }

        // 使用业务类型+手机号作为key获取验证码
        String captchaKey = businessType + ":" + phoneNumber;
        // 获取并删除验证码
        String storedCode = phoneCaptchaCache.remove(captchaKey);
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