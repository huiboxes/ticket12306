package com.codenlog.ticket.member.response;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/06/6:05 PM
 */
public class MemberLoginResponse {

    private Long id;

    private String mobile;

    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MemberLoginResponse{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
