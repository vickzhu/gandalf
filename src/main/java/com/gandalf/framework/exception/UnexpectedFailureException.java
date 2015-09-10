package com.gandalf.framework.exception;

/**
 * 类UnreachableCodeException.java的实现描述：代表未预期的失败。
 * 
 * @author gandalf 2014-4-4 下午5:02:05
 */
public class UnexpectedFailureException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -2126984451384819917L;

    public UnexpectedFailureException() {
        super();
    }

    public UnexpectedFailureException(String message) {
        super(message);
    }

    public UnexpectedFailureException(Throwable cause) {
        super(cause);
    }

    public UnexpectedFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
