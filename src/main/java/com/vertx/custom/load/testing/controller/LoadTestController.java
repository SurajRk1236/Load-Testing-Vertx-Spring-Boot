package com.vertx.custom.load.testing.controller;

import com.vertx.custom.load.testing.entity.LoadTest;
import com.vertx.custom.load.testing.repository.LoadTestRepository;
import com.vertx.custom.load.testing.requests.LoadTestRequest;
import com.vertx.custom.load.testing.responses.CommonResponse;
import com.vertx.custom.load.testing.service.LoadTestVerticle;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.vertx.core.Vertx;

import static com.vertx.custom.load.testing.constants.SuccessMessageConstants.LOAD_TESTING_SUBMITTED_SUCCESSFULLY;

@RestController
@RequestMapping("/load")
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoadTestController {

    final Vertx vertx;
    final LoadTestRepository loadTestRepository;

    public LoadTestController(LoadTestRepository loadTestRepository) {
        this.loadTestRepository = loadTestRepository;
        this.vertx = Vertx.vertx();
    }

    @PostMapping("/startTest")
    public CommonResponse<String> startLoadTest(@RequestBody LoadTestRequest loadTestRequest) {
        vertx.deployVerticle(new LoadTestVerticle(loadTestRequest));
        loadTestRepository.save(LoadTest.builder()
                .loadTestName(loadTestRequest.getLoadTestName())
                .curl(loadTestRequest.getCurl())
                .completedStatus(false)
                .noOfRequest(loadTestRequest.getNoOfRequest())
                .noOfUsers(loadTestRequest.getNoOfUsers())
                .build());
        return CommonResponse.<String>builder().data(LOAD_TESTING_SUBMITTED_SUCCESSFULLY).build();
    }
}
