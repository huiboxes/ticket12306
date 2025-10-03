package com.codenlog.ticket.member.controller;

import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.member.request.MemberRegisterRequest;
import com.codenlog.ticket.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/02/7:45 PM
 */
@RestController("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Long> count() {
        return memberService.count();
    }

    @PostMapping("/register")

    public CommonResp<Long> register(MemberRegisterRequest request) {
        return memberService.register(request);
    }

}
