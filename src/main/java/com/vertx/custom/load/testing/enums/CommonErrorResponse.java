package com.vertx.custom.load.testing.enums;

import lombok.Getter;

@Getter
public enum CommonErrorResponse {
    JWTE001("JWTE001", "Invalid credentials for user."),
    JWTE002("JWTE002", "Token doesnt exist or not expired.");

    final String code;
    final String message;

    CommonErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
