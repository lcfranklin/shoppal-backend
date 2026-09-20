package com.shoppal.mw.shared.exceptions;

import java.time.LocalDateTime;

public record ApiErrorResponseEntity(
    LocalDateTime timestamp,
    int statusCode,
    String error,
    String message,
    String path
) {
}