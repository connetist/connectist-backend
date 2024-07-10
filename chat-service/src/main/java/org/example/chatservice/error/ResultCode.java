package org.example.chatservice.error;

public enum ResultCode {
    SUCCESS(200, "Success"),
    CHAT_ROOM_NOT_FOUND(404, "해당 채팅방이 존재하지 않습니다"),
    CHAT_MESSAGES_NOT_FOUND(404, "메세지들을 가져올 수 없습니다"),
    CHAT_ROOMS_NOT_FOUND(404, "전체 채팅방을 조회할 수 없습니다."),
    UNAUTHROIZED(401, "해당 작업을 수행할 권한이 없습니다"),
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