package com.codenlog.ticket.common.exception;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/03/2:55 PM
 */
public enum BusinessExceptionEnum {

    MEMBER_MOBILE_EXIST("手机号已注册"),
    VERIFY_CAPTCH_FAILED("验证码验证失败"),
    GET_CAPTCH_FAILED("获取图片验证码失败"),
    GET_TOO_MANY_PHONE_REQUESTS("该手机号今日获取验证码次数已达上限"),
    GET_TOO_MANY_REQUESTS_PHONE_CAPTCHA("验证码发送过于频繁，请稍后再试"),
    GET_TOO_MANY_REQUESTS("请求过于频繁，请稍后再试");

    private String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessExceptionEnum{" +
                "desc='" + desc + '\'' +
                "} " + super.toString();
    }
}
