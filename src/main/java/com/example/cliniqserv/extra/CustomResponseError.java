package com.example.cliniqserv.extra;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class CustomResponseError extends Throwable {
    public CustomResponseError(Number code, String message) {
        this.code = code;
        this.message = message;
    }

    @Setter
    @Getter
    private Number code = 500;

    @Setter
    @Getter
    private String message = "Server error";
}
