package com.vertx.custom.load.testing.controller;

import com.vertx.custom.load.testing.exceptions.GenericException;
import com.vertx.custom.load.testing.responses.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vertx.custom.load.testing.constants.SuccessMessageConstants.ECHO_HEALTHY;


@RestController
@RequestMapping("/echo")
public class EchoController {

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<String> getWeatherByCity() {
        return CommonResponse.<String>builder().data(ECHO_HEALTHY).build();
    }


    @GetMapping(value = "/health/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse<String> getWeatherByCityError() {
        throw new GenericException();
    }

}
