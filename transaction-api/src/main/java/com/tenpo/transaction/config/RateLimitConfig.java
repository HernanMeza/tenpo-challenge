package com.tenpo.transaction.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Value("${app.tenpo.rate-limiter.max-petitions-per-user}")
    private int maxPetitionsPerUser;

    @Value("${app.tenpo.rate-limiter.max-petitions-time-frame}")
    private int maxPetitionsTimeFrame;

    @Value("${app.tenpo.rate-limiter.refill-petitions-per-user}")
    private int refillPetitionsPerUser;


    @Bean
    public Bucket bucket() {

        Bandwidth limit = Bandwidth.classic(maxPetitionsPerUser, Refill.greedy(refillPetitionsPerUser, Duration.ofMinutes(maxPetitionsTimeFrame)));

        return Bucket4j.builder().addLimit(limit).build();
    }
}