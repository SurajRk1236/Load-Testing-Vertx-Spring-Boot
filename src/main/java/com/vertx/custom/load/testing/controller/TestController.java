package com.vertx.custom.load.testing.controller;

import com.vertx.custom.load.testing.responses.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vertx.custom.load.testing.constants.SuccessMessageConstants.PROTECTED_ENDPOINT_WITH_BEARER_TOKEN;


@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/protected")
    public CommonResponse<String> protectedEndpoint() {
        return CommonResponse.<String>builder().data(PROTECTED_ENDPOINT_WITH_BEARER_TOKEN).build();
    }
}
