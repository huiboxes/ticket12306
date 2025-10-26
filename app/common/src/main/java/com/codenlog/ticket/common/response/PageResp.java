package com.codenlog.ticket.common.response;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/27/12:31 AM
 */
public class PageResp<T> implements Serializable {

    private Integer total;
    
    private Integer pageNum;
    
    private Integer pageSize;

    private List<T> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    
    public Integer getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}