package com.msafiri.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.msafiri.event.OrderListEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class Config {

    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public Config(KafkaProperties kafkaProperties, ObjectMapper objectMapper) {
        this.kafkaProperties = kafkaProperties;
        this.objectMapper = objectMapper;
    }



    @Bean(name = "consumerFactory")
    public ConsumerFactory<String, OrderListEvent> consumerFactory() {
        JsonDeserializer<OrderListEvent> orderListEventJsonDeserializer = new JsonDeserializer<>(OrderListEvent.class, objectMapper);
        orderListEventJsonDeserializer.addTrustedPackages("*");
        orderListEventJsonDeserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(),
                new StringDeserializer(),
                orderListEventJsonDeserializer);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrderListEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderListEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
