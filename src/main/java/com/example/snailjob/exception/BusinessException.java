package com.example.snailjob.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }

}
