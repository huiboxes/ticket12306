package com.codenlog.ticket.member.service;

import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.common.response.PageResp;
import com.codenlog.ticket.member.request.PassengerQueryRequest;
import com.codenlog.ticket.member.request.PassengerSaveRequest;
import com.codenlog.ticket.member.response.PassengerQueryResponse;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/08/9:36 PM
 */
public interface PassengerService {

    CommonResp save(PassengerSaveRequest req);

    CommonResp<PageResp<PassengerQueryResponse>> queryList(PassengerQueryRequest request);

}
