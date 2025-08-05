package com.cs.base_microservice.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.cs.base_microservice")
public class TestConfig {
    // Test configuration for integration tests
}
