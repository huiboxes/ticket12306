package com.codenlog.ticket.member.config.sms;

import org.springframework.stereotype.Component;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:07 PM
 */
@Component
public class AliyunSmsProvider implements SmsProvider {

    // @Value("${app.sms.aliyun.access-key}")
    // private String accessKey;
    //
    // @Value("${app.sms.aliyun.secret}")
    // private String secret;
    //
    // @Value("${app.sms.aliyun.sign-name}")
    // private String signName;
    //
    // @Value("${app.sms.aliyun.template-code}")
    // private String templateCode;


    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean sendVerificationCode(String phoneNumber, String code) {
        // 示例代码：
        /*
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNumber);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            return "OK".equals(response.getCode());
        } catch (ClientException e) {
            // 日志记录
            return false;
        }
        */

        // 模拟实现
        return true;
    }
}
