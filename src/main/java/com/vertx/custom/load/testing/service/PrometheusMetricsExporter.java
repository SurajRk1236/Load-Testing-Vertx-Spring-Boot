package com.vertx.custom.load.testing.service;

import io.prometheus.client.exporter.HTTPServer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PrometheusMetricsExporter implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        HTTPServer server = new HTTPServer(8081);
    }

}
