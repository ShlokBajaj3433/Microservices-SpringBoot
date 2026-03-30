package com.shlok.microservices.notification_service.config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * Factory for creating ConcurrentMessageListenerContainer with Observation support.
 * This class enables distributed tracing and metrics collection for Kafka message listeners.
 */
public class ConcurrentContainerKafkaFactory {

    private static final Logger log = LoggerFactory.getLogger(ConcurrentContainerKafkaFactory.class);
    
    private final ObservationRegistry observationRegistry;
    
    public ConcurrentContainerKafkaFactory(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }
    
    /**
     * Creates an Observation for Kafka listener operations with enhanced tracing capabilities.
     * 
     * @param topicName the name of the Kafka topic
     * @param consumerGroup the consumer group ID
     * @return an Observation instance configured for Kafka operations
     */
    public Observation createObservation(String topicName, String consumerGroup) {
        return Observation.createNotStarted(
                "kafka.listener.process",
                observationRegistry
        ).contextualName("kafka-listener")
         .lowCardinalityKeyValue("kafka.topic", topicName)
         .lowCardinalityKeyValue("kafka.consumer.group", consumerGroup);
    }
    
    /**
     * Configures a ConcurrentMessageListenerContainer with observation support.
     * 
     * @param container the container to configure
     * @param topicName the name of the Kafka topic
     * @param consumerGroup the consumer group ID
     */
    public void configureObservation(
            ConcurrentMessageListenerContainer<String, ?> container, 
            String topicName, 
            String consumerGroup) {
        
        log.info("Configuring observation for Kafka listener - Topic: {}, ConsumerGroup: {}", 
                 topicName, consumerGroup);
        
        // Configure container error handling with observation
        container.getContainerProperties().setObservationEnabled(true);
        
        log.debug("Kafka listener container configured with observation support");
    }
    
    /**
     * Gets the observation registry used for tracing.
     * 
     * @return the ObservationRegistry instance
     */
    public ObservationRegistry getObservationRegistry() {
        return observationRegistry;
    }
}
