package org.example.boardservice.board.dto;

import lombok.Builder;
import lombok.Data;
import org.example.boardservice.error.ResultCode;

import java.io.Serializable;

@Data
@Builder
public class RestResponse<T> implements Serializable {
    private boolean success;
    private String message;
    private T data;

    public static <T> RestResponse<T> success(T data) {
        return RestResponse.<T>builder()
                .success(true)
                .message("success")
                .data(data)
                .build();
    }

    public static <T> RestResponse<T> success() {
        return RestResponse.<T>builder()
                .success(true)
                .message("success")
                .build();
    }

    public static <T> RestResponse<T> error(ResultCode resultCode) {
        return RestResponse.<T>builder()
                .success(false)
//                .code(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }
}