package com.vertx.custom.load.testing.utils;

import io.prometheus.client.Counter;
import io.prometheus.client.Summary;

public class Metrics {
    public static final Counter requests = Counter.build()
            .name("http_requests_total")
            .help("Total HTTP requests.")
            .labelNames("loadTestName", "status", "label_1", "label_2")
            .register();

    public static final Summary responseDuration = Summary.build()
            .name("http_response_duration_seconds")
            .help("Duration of HTTP responses.")
            .labelNames("loadTestName_3", "label_3")
            .register();
}
