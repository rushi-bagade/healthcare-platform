package com.healthcare.profile.publisher;

import com.healthcare.profile.dto.PatientCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientEventPublisher {

    private static final String TOPIC = "patient-created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PatientEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPatientCreated(PatientCreatedEvent event) {
        kafkaTemplate.send(TOPIC,
                event.patientId().toString(), event);
    }
}