package com.healthcare.fhir.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.healthcare.fhir.dto.PatientCreatedEvent;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, PatientCreatedEvent> consumerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fhir-integration-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        JsonDeserializer<PatientCreatedEvent> jsonDeserializer =
                new JsonDeserializer<>(PatientCreatedEvent.class, false);

        jsonDeserializer.addTrustedPackages("com.healthcare.fhir.dto");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public DefaultErrorHandler kafkaErrorHandler(
            KafkaTemplate<String, Object> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, exception) -> new TopicPartition(
                                record.topic() + ".DLT",
                                record.partition()
                        )
                );

        // 2 seconds between retries, with 3 retries after the initial attempt
        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(
                        recoverer,
                        new FixedBackOff(2000L, 3L)
                );

        return errorHandler;
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, PatientCreatedEvent>
            kafkaListenerContainerFactory(
                    ConsumerFactory<String, PatientCreatedEvent> consumerFactory,
                    DefaultErrorHandler kafkaErrorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, PatientCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(kafkaErrorHandler);

        return factory;
    }
}