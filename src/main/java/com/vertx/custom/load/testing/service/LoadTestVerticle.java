package com.vertx.custom.load.testing.service;

import com.vertx.custom.load.testing.requests.LoadTestRequest;
import com.vertx.custom.load.testing.utils.CurlParser;
import com.vertx.custom.load.testing.utils.Metrics;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class LoadTestVerticle extends AbstractVerticle {
    public static final String LOAD_TEST_NAME = "loadTestName";
    public static final String STATUS = "status";
    public static final String HEADERS = "headers";
    private final LoadTestRequest loadTestRequest;

    private final Vertx vertx;
    private final HttpClient client;


    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);
    private final AtomicInteger requestCount = new AtomicInteger(0);
    private final AtomicLong totalResponseTime = new AtomicLong(0);
    private final AtomicInteger status2xx = new AtomicInteger(0);
    private final AtomicInteger status4xx = new AtomicInteger(0);
    private final AtomicInteger status5xx = new AtomicInteger(0);


    public LoadTestVerticle(LoadTestRequest loadTestRequest) {
        this.loadTestRequest = loadTestRequest;
        this.vertx = Vertx.vertx();
        HttpClientOptions options = new HttpClientOptions().setDefaultPort(8080);
        this.client = vertx.createHttpClient(options);
    }

    @Override
    public void start() {
        for (int i = 0; i < loadTestRequest.getNoOfUsers(); i++) {
            int finalI = i;
            vertx.executeBlocking(promise -> {
                sendRequestsConcurrently(loadTestRequest.getCurl(), loadTestRequest.getLoadTestName(), loadTestRequest.getNoOfRequest());
                promise.complete();
            }, res -> {
                if (res.succeeded()) {
                    System.out.println("All requests triggered for user " + finalI);
                } else {
                    System.err.println("Failed to trigger requests for user " + finalI);
                }
            });
        }
    }

    private void sendRequestsConcurrently(String curlCommand, String loadTestName, int noOfRequests) {
        for (int i = 0; i < noOfRequests; i++) {
            sendRequest(curlCommand, loadTestName);
        }
    }


    private void sendRequest(String curlCommand, String loadTestName) {
        long startTime = System.nanoTime();
        requestCount.incrementAndGet();

        Map<String, String> curlDetails = CurlParser.parseCurl(curlCommand);
        String url = curlDetails.get("url");
        HttpMethod method = HttpMethod.valueOf(curlDetails.getOrDefault("method", "GET"));


        client.request(method, url)
                .compose(request -> {
                    if (curlDetails.containsKey(HEADERS)) {
                        for (Map.Entry<String, String> entry : CurlParser.parseHeaders(curlDetails.get(HEADERS)).entrySet()) {
                            request.putHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    return request.send();
                })
                .onSuccess(response -> {
                    long endTime = System.nanoTime();
                    long responseTime = endTime - startTime;
                    totalResponseTime.addAndGet(responseTime);

                    // Process response status
                    int statusCode = response.statusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        successCount.incrementAndGet();
                        status2xx.incrementAndGet();
                        Metrics.requests.labels(LOAD_TEST_NAME, loadTestName, STATUS, "2xx").inc();
                    } else if (statusCode >= 400 && statusCode < 500) {
                        errorCount.incrementAndGet();
                        status4xx.incrementAndGet();
                        Metrics.requests.labels(LOAD_TEST_NAME, loadTestName, STATUS, "4xx").inc();
                    } else if (statusCode >= 500) {
                        errorCount.incrementAndGet();
                        status5xx.incrementAndGet();
                        Metrics.requests.labels(LOAD_TEST_NAME, loadTestName, STATUS, "5xx").inc();
                    }

                    Metrics.responseDuration.labels(LOAD_TEST_NAME, loadTestName).observe(responseTime / (1e9 * 1e9)); // Convert nanoseconds to seconds
                })
                .onFailure(error -> {
                    errorCount.incrementAndGet();
                    Metrics.requests.labels(LOAD_TEST_NAME, loadTestName, STATUS, "error").inc();
                    System.err.println("Request error: " + error.getMessage());
                });
        //TODO update the status to completed after by fetching the testname
    }
}
