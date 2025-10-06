package com.codenlog.ticket.member.config.sms;

import org.springframework.stereotype.Component;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:09 PM
 */
@Component
public class TencentSmsProvider implements SmsProvider {

    // @Value("${app.sms.tencent.secret-id}")
    // private String secretId;
    //
    // @Value("${app.sms.tencent.secret-key}")
    // private String secretKey;
    //
    // @Value("${app.sms.tencent.sdk-app-id}")
    // private String sdkAppId;
    //
    // @Value("${app.sms.tencent.sign-name}")
    // private String signName;
    //
    // @Value("${app.sms.tencent.template-id}")
    // private String templateId;

    @Override
    public String getName() {
        return "tencent";
    }

    @Override
    public boolean sendVerificationCode(String phoneNumber, String code) {

        // 模拟实现
        return true;
    }
}
