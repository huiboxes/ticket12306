package com.codenlog.ticket.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/01/11:46 PM
 */

@RestController
public class TestController {


    @GetMapping("/test")
    public String test(){
        return "hello world";
    }
}
