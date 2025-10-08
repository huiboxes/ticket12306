package com.codenlog.ticket.member.service;

import cn.hutool.core.bean.BeanUtil;
import com.codenlog.ticket.common.context.LoginMemberContext;
import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.common.util.SnowUtil;
import com.codenlog.ticket.member.domain.Passenger;
import com.codenlog.ticket.member.mapper.PassengerMapper;
import com.codenlog.ticket.member.request.PassengerSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/08/9:38 PM
 */
@Service
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private PassengerMapper passengerMapper;

    @Override
    public CommonResp save(PassengerSaveRequest req) {
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setMemberId(LoginMemberContext.getMember().getId());
        passenger.setId(SnowUtil.getSnowflakeNextId());
        int insert = passengerMapper.insertSelective(passenger);

        if (insert != 1) {
            return CommonResp.fail("新增乘客失败");
        }
        return CommonResp.success("新增乘客成功");
    }


}
