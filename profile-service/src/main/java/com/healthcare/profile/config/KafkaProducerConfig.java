package com.healthcare.profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
// KafkaProducerConfig is a Spring configuration class that defines the Kafka producer factory and KafkaTemplate beans. It configures the bootstrap server and serializers, allowing our application to publish Java event objects as JSON messages to Kafka topics through Spring Kafka's KafkaTemplate.
@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory(
            org.springframework.core.env.Environment environment) {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,   // This tells the producer where to find Kafka.
                environment.getProperty("spring.kafka.bootstrap-servers",
                        "localhost:9092"));

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);

        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(    // KafkaTemplate is the high-level Spring Kafka component your application uses to send messages.
            ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}

// A ProducerFactory creates Kafka producer instances using the supplied configuration.
// The generic types mean:
// String — the Kafka message key.
// Object — the message value, such as your PatientCreatedEvent.