package com.codenlog.ticket.member.service;

import cn.hutool.core.collection.CollectionUtil;
import com.codenlog.ticket.common.exception.BusinessException;
import com.codenlog.ticket.common.exception.BusinessExceptionEnum;
import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.common.util.SnowUtil;
import com.codenlog.ticket.member.domain.Member;
import com.codenlog.ticket.member.domain.MemberExample;
import com.codenlog.ticket.member.mapper.MemberMapper;
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

    public CommonResp<Long> count() {

        MemberExample example = new MemberExample();
        example.createCriteria().andIdEqualTo(1L);

        return new CommonResp<Long>(memberMapper.countByExample(example));
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

    private boolean hasMember(String mobile) {
        MemberExample condition = new MemberExample();
        condition.createCriteria().andMobileEqualTo(mobile);
        List<Member> memberList = memberMapper.selectByExample(condition);
        return CollectionUtil.isNotEmpty(memberList);
    }
}
