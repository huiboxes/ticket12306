package com.codenlog.ticket.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/06
 */
public class JwtUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

    private static final String key = "TicketTicketTicket";

    public static String createToken(Map<String, Object> payload) {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 24);

        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        String token = JWTUtil.createToken(payload, key.getBytes());
        LOG.info("生成JWT token：{}", token);
        return token;
    }

    public static boolean validate(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        boolean validate = jwt.validate(0);
        LOG.info("JWT token校验结果：{}", validate);
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        if (!jwt.validate(0)) {
            LOG.info("JWT token已过期或无效，无法获取原始内容");
            return null;
        }
        
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        LOG.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

    public static void main(String[] args) {

        HashMap<String, Object> res = new HashMap<>();
        res.put("id", 1);
        res.put("mobile", 1);
        String token = createToken(res);

        validate(token);

        getJSONObject(token);
    }
}
