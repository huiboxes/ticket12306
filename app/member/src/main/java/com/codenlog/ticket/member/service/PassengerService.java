package com.codenlog.ticket.member.service;

import com.codenlog.ticket.common.response.CommonResp;
import com.codenlog.ticket.member.request.PassengerSaveRequest;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/08/9:36 PM
 */
public interface PassengerService {

    CommonResp save(PassengerSaveRequest req);

}
