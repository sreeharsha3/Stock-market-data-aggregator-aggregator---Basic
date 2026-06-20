package com.definedge.client.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String message;

    private Integer status;

    private LocalDateTime timestamp;
}
