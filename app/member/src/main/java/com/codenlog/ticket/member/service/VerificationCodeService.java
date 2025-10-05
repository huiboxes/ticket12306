package com.codenlog.ticket.member.service;

import com.codenlog.ticket.member.domain.GraphCaptcha;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/9:00 PM
 */
public interface VerificationCodeService {

    /**
     * 生成图形验证码
     * @return 图形验证码
     */
    GraphCaptcha generateGraphCaptcha();

    /**
     * 验证图形验证码
     * @param uuid 验证码标识
     * @param code 验证码
     * @return 是否验证通过
     */
    boolean verifyGraphCaptcha(String uuid, String code);

    /**
     * 发送手机验证码
     * @param phoneNumber 手机号
     * @param ipAddress 客户端IP
     * @param businessType 业务类型
     * @param graphCaptchaUuid 图形验证码标识（可能为null）
     * @param graphCaptchaCode 图形验证码（可能为null）
     * @return 是否发送成功
     */
    boolean sendPhoneCaptcha(String phoneNumber, String ipAddress, String businessType,
                             String graphCaptchaUuid, String graphCaptchaCode);

    /**
     * 验证手机验证码
     * @param phoneNumber 手机号
     * @param code 验证码
     * @param businessType 业务类型
     * @return 是否验证通过
     */
    boolean verifyPhoneCaptcha(String phoneNumber, String code, String businessType);

    /**
     * 检查是否需要图形验证码
     * @param ipAddress 客户端IP
     * @return 是否需要
     */
    boolean needGraphCaptcha(String ipAddress);

}