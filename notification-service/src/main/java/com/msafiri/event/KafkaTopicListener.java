package com.msafiri.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaTopicListener {

    private final ObjectMapper objectMapper;

    public KafkaTopicListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @KafkaListener(
            topics = {"order-notification-topic"},
            groupId = "order-notification-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleNotification(OrderListEvent orderPlacedEvent) {
        try {
            log.info("Notification sent for order number: {}", objectMapper.writeValueAsString(orderPlacedEvent));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
