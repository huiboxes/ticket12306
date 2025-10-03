package com.codenlog.ticket.common.exception;

/**
 * @Author: devhui@foxmail.com
 * @Date: 2025/10/03/2:22 PM
 */
public class BusinessException extends RuntimeException {

    private BusinessExceptionEnum e;

    private BusinessException(BusinessExceptionEnum e) {
        this.e = e;
    }

    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum e) {
        this.e = e;
    }

    private BusinessException(String message, Object ...params) {
        super(String.format(message, params));
    }

    public static BusinessException of(BusinessExceptionEnum e) {
        return new BusinessException(e);
    }

    public static BusinessException of(String message, Object ...params) {
        return new BusinessException(message, params);
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
