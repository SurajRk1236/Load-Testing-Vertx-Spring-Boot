package com.vertx.custom.load.testing.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {


//    @Value("${secret.key}")
//    String secretKey;
//
//    @Value("${token.acessTime}")
//    long accessTime;
//
//    @Value("${token.refreshTime}")
//    long refreshTime;
}
