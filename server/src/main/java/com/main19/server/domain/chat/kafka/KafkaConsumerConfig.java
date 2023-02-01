package com.main19.server.domain.chat.kafka;

import com.google.common.collect.ImmutableMap;
import com.main19.server.domain.chat.dto.ChatDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.id}")
    private String id;

    @Value("${kafka.broker}")
    private String broker;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatDto.Post> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatDto.Post> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ChatDto.Post> consumerFactory() {
        JsonDeserializer<ChatDto.Post> deserializer = new JsonDeserializer<>(ChatDto.Post.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        ImmutableMap<String, Object> config = ImmutableMap.<String, Object>builder()
            .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
            .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
            .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
            .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
            .put("group.id", id)
            .build();

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

}
