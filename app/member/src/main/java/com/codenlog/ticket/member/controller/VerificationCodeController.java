package com.codenlog.ticket.member.controller;

import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.member.domain.GraphCaptcha;
import com.codenlog.ticket.member.request.SendPhoneCaptchaRequest;
import com.codenlog.ticket.member.request.VerifyPhoneCaptchaRequest;
import com.codenlog.ticket.member.service.VerificationCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/9:07 PM
 */
@RestController
@RequestMapping("/captcha")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    @Autowired
    public VerificationCodeController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    /**
     * 获取图形验证码
     */
    @GetMapping("/graph")
    public CommonResp<GraphCaptcha> getGraphCaptcha() {
        return new CommonResp<GraphCaptcha>(verificationCodeService.generateGraphCaptcha());
    }

    /**
     * 检查是否需要图形验证码
     */
    @GetMapping("/need-graph")
    public CommonResp<Boolean> needGraphCaptcha(HttpServletRequest request) {
        String ipAddress = getClientIp(request);
        boolean need = verificationCodeService.needGraphCaptcha(ipAddress);

        CommonResp<Boolean> res = new CommonResp<>(need);
        res.setMessage(need ? "需要图形验证码" : "不需要图形验证码");

        return res;
    }

    /**
     * 发送手机验证码
     */
    @PostMapping("/phone")
    public CommonResp<Boolean> sendPhoneCaptcha(@Valid @RequestBody SendPhoneCaptchaRequest req,
                                                HttpServletRequest request) {

        String ipAddress = getClientIp(request);
        boolean success = verificationCodeService.sendPhoneCaptcha(
                req.getPhoneNumber(), ipAddress, req.getBusinessType(), 
                req.getGraphCaptchaUuid(), req.getGraphCaptchaCode());

        CommonResp<Boolean> res = new CommonResp<>(success);
        res.setMessage(success ? "验证码已发送，请注意查收" : "验证码发送失败，请重试");
        return res;
    }

    /**
     * 验证手机验证码
     */
    @PostMapping("/verify-phone")
    public CommonResp<Boolean> verifyPhoneCaptcha(@Valid @RequestBody VerifyPhoneCaptchaRequest req) {

        boolean valid = verificationCodeService.verifyPhoneCaptcha(
                req.getPhoneNumber(), req.getCode(), req.getBusinessType());

        CommonResp<Boolean> res = new CommonResp<>(valid);
        res.setMessage(valid ? "验证码验证成功" : "验证码验证失败");

        return res;
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }

}