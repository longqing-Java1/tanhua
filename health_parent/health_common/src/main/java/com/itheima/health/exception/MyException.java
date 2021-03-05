package com.itheima.health.exception;

/**
 * <p>
 * 自定义异常
 * </p>
 *
 * @author: Eric
 * @since: 2021/2/4
 */
public class MyException extends RuntimeException {

    /**
     * 构建 就要传入提示的信息
     * @param message
     */
    public MyException(String message){
        super(message);
    }
}
