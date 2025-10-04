package com.codenlog.ticket.member.domain;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/8:24 PM
 */
public class GraphCaptcha {

    /**
     * 验证码唯一标识
     */
    private String uuid;

    /**
     * 验证码图片数据（Base64编码）
     */
    private String imageBase64;

    /**
     * 验证码过期时间（毫秒）
     */
    private long expireTime;

    public GraphCaptcha(String uuid, String imageBase64, long expireTime) {
        this.uuid = uuid;
        this.imageBase64 = imageBase64;
        this.expireTime = expireTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
