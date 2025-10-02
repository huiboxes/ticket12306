package com.codenlog.ticket.member.service;

import com.codenlog.ticket.member.domain.MemberExample;
import com.codenlog.ticket.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/02/7:41 PM
 */
@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public Long count() {

        MemberExample example = new MemberExample();
        example.createCriteria().andIdEqualTo(1L);

        return memberMapper.countByExample(example);
    }
}
