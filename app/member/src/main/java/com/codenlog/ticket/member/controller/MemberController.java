package com.codenlog.ticket.member.controller;

import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.member.MemberLoginResponse;
import com.codenlog.ticket.member.request.MemberLoginRequest;
import com.codenlog.ticket.member.request.MemberRegisterRequest;
import com.codenlog.ticket.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/02/7:45 PM
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/count")
    public CommonResp<Long> count() {
        return memberService.count();
    }

    @PostMapping("/register")
    public CommonResp<MemberLoginResponse> register(@Valid @RequestBody MemberRegisterRequest request) {
        return memberService.register(request);
    }
    
    @PostMapping("/login")
    public CommonResp<MemberLoginResponse> login(@Valid @RequestBody MemberLoginRequest request,
                                  HttpServletRequest servletRequest) {
        return memberService.login(request);
    }
}