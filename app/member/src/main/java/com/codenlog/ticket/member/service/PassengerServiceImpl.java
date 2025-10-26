package com.codenlog.ticket.member.service;

import cn.hutool.core.bean.BeanUtil;
import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.common.response.PageResp;
import com.codenlog.ticket.common.util.SnowUtil;
import com.codenlog.ticket.member.domain.Passenger;
import com.codenlog.ticket.member.domain.PassengerExample;
import com.codenlog.ticket.member.mapper.PassengerMapper;
import com.codenlog.ticket.member.request.PassengerQueryRequest;
import com.codenlog.ticket.member.request.PassengerSaveRequest;
import com.codenlog.ticket.member.response.PassengerQueryResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        passenger.setId(SnowUtil.getSnowflakeNextId());
        int insert = passengerMapper.insertSelective(passenger);

        if (insert != 1) {
            return CommonResp.fail("新增乘客失败");
        }
        return CommonResp.success("新增乘客成功");
    }

    @Override
    public CommonResp<PageResp<PassengerQueryResponse>> queryList(PassengerQueryRequest request) {
        if (request.getPage() == null) {
            request.setPage(1);
        }
        if (request.getSize() == null) {
            request.setSize(10);
        }
        
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        
        if(Objects.nonNull(request.getMemberId())) {
            criteria.andMemberIdEqualTo(request.getMemberId());
        }
        
        PageHelper.startPage(request.getPage(), request.getSize());
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);

        PageInfo<Passenger> pageInfo = new PageInfo<>(passengerList);
        List<PassengerQueryResponse> list = BeanUtil.copyToList(passengerList, PassengerQueryResponse.class);

        PageResp<PassengerQueryResponse> pageResp = new PageResp<>();
        pageResp.setTotal((int) pageInfo.getTotal());
        pageResp.setList(list);
        pageResp.setPageNum(pageInfo.getPageNum());
        pageResp.setPageSize(pageInfo.getPageSize());
        
        return CommonResp.success(pageResp);
    }
}