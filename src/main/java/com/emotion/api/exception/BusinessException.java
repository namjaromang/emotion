package com.emotion.api.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -7098286411632701486L;

    private final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException() {
        super(ErrorCode.INVALID_INPUT_VALUE.getMessage());
        this.errorCode = ErrorCode.INVALID_INPUT_VALUE;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}

