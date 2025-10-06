package com.codenlog.ticket.member.service;

import cn.hutool.core.collection.CollectionUtil;
import com.codenlog.ticket.common.exception.BusinessException;
import com.codenlog.ticket.common.exception.BusinessExceptionEnum;
import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.common.util.SnowUtil;
import com.codenlog.ticket.member.domain.Member;
import com.codenlog.ticket.member.domain.MemberExample;
import com.codenlog.ticket.member.mapper.MemberMapper;
import com.codenlog.ticket.member.request.MemberLoginRequest;
import com.codenlog.ticket.member.request.MemberRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/02/7:41 PM
 */
@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private VerificationCodeService verificationCodeService;

    public CommonResp<Long> count() {
        return new CommonResp<Long>(memberMapper.countByExample(null));
    }

    public CommonResp<Long> register(MemberRegisterRequest request) {
        String mobile = request.getMobile();
        if(hasMember(mobile)){
            throw BusinessException.of(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(request.getMobile());
        memberMapper.insertSelective(member);

        return new CommonResp(member.getId());
    }
    
    public boolean hasMember(String mobile) {
        MemberExample condition = new MemberExample();
        condition.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(condition);
        return CollectionUtil.isNotEmpty(memberList);
    }
    
    public CommonResp<Long> login(MemberLoginRequest request) {
        boolean valid = verificationCodeService.verifyPhoneCaptcha(
                request.getMobile(), request.getCode(), "LOGIN");
        if (!valid) {
            throw BusinessException.of(BusinessExceptionEnum.VERIFY_CAPTCH_FAILED);
        }

        MemberExample condition = new MemberExample();
        condition.createCriteria().andMobileEqualTo(request.getMobile());
        List<Member> memberList = memberMapper.selectByExample(condition);
        if (CollectionUtil.isEmpty(memberList)) {
            // 用户不存在，自动注册
            MemberRegisterRequest registerRequest = new MemberRegisterRequest();
            registerRequest.setMobile(request.getMobile());
            return register(registerRequest);
        }
        return new CommonResp<Long>(memberList.get(0).getId());
    }
}