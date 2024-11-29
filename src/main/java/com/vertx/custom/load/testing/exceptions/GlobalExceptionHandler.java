package com.vertx.custom.load.testing.exceptions;

import com.vertx.custom.load.testing.responses.CommonResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

import static com.vertx.custom.load.testing.constants.CommonConstants.DELIMITER;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return getErrorResponse(e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(DELIMITER)));
    }

    @ExceptionHandler(GenericException.class)
    @ResponseBody
    public CommonResponse<?> handleException(GenericException exception) {
        return getErrorResponse(exception);
    }

    private CommonResponse<?> getErrorResponse(GenericException exception) {
        return CommonResponse.builder()
                .errorMessage(exception.getErrorResponse().getMessage())
                .code(exception.getErrorResponse().getCode()).build();
    }

    private CommonResponse<?> getErrorResponse(String message) {
        return CommonResponse.builder()
                .errorMessage(message).build();
    }
}
