package com.qianmo.minepanel.Enum;

public enum ResponseCode{
    SUCCESS(200),
    ERROR(500),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    NOT_AVAILABLE(406),
    BAD_REQUEST(400);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int get() {
        return code;
    }
}
