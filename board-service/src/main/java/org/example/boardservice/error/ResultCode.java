package org.example.boardservice.error;

public enum ResultCode {
    SUCCESS(200, "Success"),
    USER_STAR_ALREADY_EXISTS(400, "User already exists"),
    USER_STAR_NOT_FOUND(400, "User does not exist"),
    UNAUTHROIZED(401, "해당 작업을 수행할 권한이 없습니다"),
    LAB_NOT_FOUND(401,"해당 랩이 존재하지 않습니다 "),
    INTERNAL_SERVER_ERROR(500, "Internal server error");
    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}