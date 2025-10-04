package com.codenlog.ticket.member.config.captcha;

import com.codenlog.ticket.member.domain.GraphCaptcha;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:39 PM
 */
public interface GraphCaptchaGenerator {

    /**
     * 生成图形验证码
     * @param width 宽度
     * @param height 高度
     * @param length 字符长度
     * @return 图形验证码
     */
    GraphCaptcha generate(int width, int height, int length);

    /**
     * 验证图形验证码
     * @param uuid 验证码标识
     * @param code 用户输入的验证码
     * @return 是否验证通过
     */
    boolean verify(String uuid, String code);
}
