package com.main19.server.domain.chat.kafka;

import com.google.common.collect.ImmutableMap;
import com.main19.server.domain.chat.dto.ChatDto;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${kafka.id}")
    private String id;

    @Value("${kafka.broker}")
    private String broker;

    @Bean
    public ProducerFactory<String, ChatDto.Post> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Map<String, Object> kafkaProducerConfiguration() {
        return ImmutableMap.<String, Object>builder()
            .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
            .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
            .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
            .put("group.id", id)
            .build();
    }

    @Bean
    public KafkaTemplate<String, ChatDto.Post> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}