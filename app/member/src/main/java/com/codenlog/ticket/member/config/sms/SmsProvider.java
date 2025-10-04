package com.codenlog.ticket.member.config.sms;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:05 PM
 */
public interface SmsProvider {

    /**
     * 获取提供商名称
     */
    String getName();

    /**
     * 发送短信验证码
     * @param phoneNumber 手机号
     * @param code 验证码
     * @return 是否发送成功
     */
    boolean sendVerificationCode(String phoneNumber, String code);

}
