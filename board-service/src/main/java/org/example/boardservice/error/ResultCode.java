package org.example.boardservice.error;

import org.springframework.http.HttpStatus;

public enum ResultCode {
    SUCCESS(200, "Success"),
    INVALID_REQUEST(400, "Invalid Request"),
    USER_STAR_ALREADY_EXISTS(400, "User already exists"),
    BOARD_NOT_FOUND(400, "해당 Id의 Board가 존재하지 않습니다."),
    USER_STAR_NOT_FOUND(400, "User does not exist"),
    UNAUTHROIZED(401, "해당 작업을 수행할 권한이 없습니다"),
    LAB_NOT_FOUND(401,"해당 랩이 존재하지 않습니다 "),
    COMMENT_NOT_FOUND(401, "댓글이 존재하지 않습니다"),
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