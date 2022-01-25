package com.kamal.challenge.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Validation Errors!")
public class BadRequestException extends RuntimeException {
    private String uiErrorCode;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String uiErrorCode, String message) {
        super(message);
        this.uiErrorCode = uiErrorCode;
    }


}
