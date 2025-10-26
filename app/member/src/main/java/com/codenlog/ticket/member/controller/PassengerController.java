package com.codenlog.ticket.member.controller;

import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.member.request.PassengerSaveRequest;
import com.codenlog.ticket.member.response.PassengerQueryResponse;
import com.codenlog.ticket.member.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/08/9:48 PM
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody PassengerSaveRequest req) {
        return passengerService.save(req);
    }

    @GetMapping("/list/{memberId}")
    public CommonResp<List<PassengerQueryResponse>> queryList(@PathVariable Long memberId) {
        return passengerService.queryList(memberId);
    }

}
