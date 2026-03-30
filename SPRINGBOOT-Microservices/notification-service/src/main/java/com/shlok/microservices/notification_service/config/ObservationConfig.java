package com.shlok.microservices.notification_service.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.KafkaHeaders;

@Configuration
public class ObservationConfig {
    
    @Bean
    ObservedAspect observedAspect(ObservationRegistry registry) {
        return new ObservedAspect(registry);
    }

    @Bean
    public ConcurrentContainerKafkaFactory concurrentContainerKafkaFactory(ObservationRegistry observationRegistry) {
        return new ConcurrentContainerKafkaFactory(observationRegistry);
    }
}
