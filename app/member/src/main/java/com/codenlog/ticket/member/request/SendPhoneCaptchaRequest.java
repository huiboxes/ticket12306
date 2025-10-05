package com.codenlog.ticket.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 发送手机验证码请求
 */
public class SendPhoneCaptchaRequest {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{8}$", message = "手机号格式错误")
    private String phoneNumber;

    private String graphCaptchaUuid;

    private String graphCaptchaCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGraphCaptchaUuid() {
        return graphCaptchaUuid;
    }

    public void setGraphCaptchaUuid(String graphCaptchaUuid) {
        this.graphCaptchaUuid = graphCaptchaUuid;
    }

    public String getGraphCaptchaCode() {
        return graphCaptchaCode;
    }

    public void setGraphCaptchaCode(String graphCaptchaCode) {
        this.graphCaptchaCode = graphCaptchaCode;
    }
}