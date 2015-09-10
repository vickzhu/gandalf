package com.gandalf.framework.exception;

/**
 * 类UnreachableCodeException.java的实现描述：不能达到的代码异常
 * 
 * @author gandalf 2014-4-4 下午5:02:05
 */
public class UnreachableCodeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 691220790884661549L;

    public UnreachableCodeException() {
        super();
    }

    public UnreachableCodeException(String message) {
        super(message);
    }

    public UnreachableCodeException(Throwable cause) {
        super(cause);
    }

    public UnreachableCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
