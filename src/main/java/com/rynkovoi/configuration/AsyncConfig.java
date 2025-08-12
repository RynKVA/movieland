package com.rynkovoi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean
    public Executor enrichmentExecutor(){
        return Executors.newCachedThreadPool();
    }
}
