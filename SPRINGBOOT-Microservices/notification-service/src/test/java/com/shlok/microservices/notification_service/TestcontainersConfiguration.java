package com.shlok.microservices.notification_service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;


@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		DockerImageName kafkaImage = DockerImageName.parse("apache/kafka-native:latest")
				.asCompatibleSubstituteFor("confluentinc/cp-kafka");
		return new KafkaContainer(kafkaImage);
	}

}
