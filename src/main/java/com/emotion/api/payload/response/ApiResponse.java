package com.emotion.api.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String code;
    private String message;


    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

