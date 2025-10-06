package com.codenlog.ticket.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/03/3:26 PM
 */
public class MemberRegisterRequest {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberRegisterRequest{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
