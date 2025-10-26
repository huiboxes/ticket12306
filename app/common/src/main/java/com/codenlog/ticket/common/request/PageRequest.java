package com.codenlog.ticket.common.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/27/12:23 AM
 */
public class PageRequest {

    @NotNull(message = "页码不能为空")
    private Integer page = 1;

    @NotNull(message = "页大小不能为空")
    @Max(value = 100, message = "每页大小不能超过100")
    private Integer size = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
