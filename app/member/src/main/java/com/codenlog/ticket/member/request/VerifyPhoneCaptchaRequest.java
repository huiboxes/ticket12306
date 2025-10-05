package com.codenlog.ticket.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 验证手机验证码请求
 */
public class VerifyPhoneCaptchaRequest {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{8}$", message = "手机号格式错误")
    private String phoneNumber;

    @NotBlank(message = "验证码不能为空")
    private String code;

    @NotBlank(message = "业务类型不能为空")
    private String businessType;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}