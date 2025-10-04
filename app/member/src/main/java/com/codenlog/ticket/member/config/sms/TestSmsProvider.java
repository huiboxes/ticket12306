package com.codenlog.ticket.member.config.sms;

import org.springframework.stereotype.Component;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:11 PM
 */
@Component
public class TestSmsProvider implements SmsProvider {

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public boolean sendVerificationCode(String phoneNumber, String code) {
        // 测试模式下不发送真实短信，仅打印日志
        System.out.println("[测试模式] 向手机号 " + phoneNumber + " 发送验证码: " + code);
        return true;
    }
}
