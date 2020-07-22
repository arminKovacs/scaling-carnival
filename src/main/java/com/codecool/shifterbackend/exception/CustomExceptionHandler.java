package com.codecool.shifterbackend.exception;

import com.codecool.shifterbackend.controller.dto.ShiftAssignmentDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ShiftAlreadyAssignedException.class)
    public final ResponseEntity<Object> handleShiftAlreadyAssignedException(ShiftAssignmentDetails exception) {
        return new ResponseEntity("Shift already assigned to user", HttpStatus.BAD_REQUEST);
    }
}
