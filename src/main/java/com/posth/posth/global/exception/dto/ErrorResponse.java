package com.posth.posth.global.exception.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
}
