package com.codenlog.ticket.member.request;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/26/11:12 PM
 */
public class PassengerQueryRequest {


    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", memberId=").append(memberId);
        sb.append("]");
        return sb.toString();
    }
}