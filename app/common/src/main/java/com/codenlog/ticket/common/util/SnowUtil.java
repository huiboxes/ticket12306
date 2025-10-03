package com.codenlog.ticket.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/03/3:32 PM
 */
public class SnowUtil {

    private static long dataCenterId = 1;  //数据中心
    private static long workerId = 1;     //机器标识

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
    }
}
