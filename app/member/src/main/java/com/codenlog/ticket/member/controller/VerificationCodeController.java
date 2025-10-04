package com.codenlog.ticket.member.controller;

import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.member.domain.GraphCaptcha;
import com.codenlog.ticket.member.service.VerificationCodeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/04/9:07 PM
 */
@RestController("captcha")
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
    public ResponseEntity<Map<String, Boolean>> needGraphCaptcha(HttpServletRequest request) {
        String ipAddress = getClientIp(request);
        boolean need = verificationCodeService.needGraphCaptcha(ipAddress);

        Map<String, Boolean> result = new HashMap<>();
        result.put("needGraphCaptcha", need);
        return ResponseEntity.ok(result);
    }

    /**
     * 发送手机验证码
     */
    @PostMapping("/phone")
    public ResponseEntity<Map<String, String>> sendPhoneCaptcha(
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String graphCaptchaUuid,
            @RequestParam(required = false) String graphCaptchaCode,
            HttpServletRequest request) {

        String ipAddress = getClientIp(request);
        boolean success = verificationCodeService.sendPhoneCaptcha(
                phoneNumber, ipAddress, graphCaptchaUuid, graphCaptchaCode);

        Map<String, String> result = new HashMap<>();
        if (success) {
            result.put("message", "验证码已发送，请注意查收");
            return ResponseEntity.ok(result);
        } else {
            result.put("message", "验证码发送失败，请重试");
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 验证手机验证码
     */
    @PostMapping("/verify-phone")
    public ResponseEntity<Map<String, Boolean>> verifyPhoneCaptcha(
            @RequestParam String phoneNumber,
            @RequestParam String code) {

        boolean valid = verificationCodeService.verifyPhoneCaptcha(phoneNumber, code);

        Map<String, Boolean> result = new HashMap<>();
        result.put("valid", valid);
        return ResponseEntity.ok(result);
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
