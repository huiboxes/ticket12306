package com.codenlog.ticket.member.request;

import jakarta.validation.constraints.NotBlank;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/03/3:26 PM
 */
public class MemberRegisterRequest {

    @NotBlank(message = "手机号不能为空")
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
