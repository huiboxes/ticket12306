package com.codenlog.ticket.common.context;

import com.codenlog.ticket.common.response.MemberLoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/08/11:43 PM
 */
public class LoginMemberContext {

    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberContext.class);

    private static ThreadLocal<MemberLoginResponse> member = new ThreadLocal<>();

    public static MemberLoginResponse getMember() {
        return member.get();
    }

    public static void setMember(MemberLoginResponse member) {
        LoginMemberContext.member.set(member);
    }

    public static Long getId() {
        try {
            return getMember().getId();
        } catch (Exception e) {
            LOG.error("获取登录会员信息异常", e);
            throw e;
        }
    }

}
