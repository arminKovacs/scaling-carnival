package com.codecool.shifterbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShiftAlreadyAssignedException extends RuntimeException{
    ShiftAlreadyAssignedException(String exception) {
        super(exception);
    }
}
