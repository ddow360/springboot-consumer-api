package com.dow.design.springboot.consumer.api.config;

import com.dow.design.springboot.consumer.api.error.DeserializationErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    // Configuring Kafka Listener Container Factory
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setContainerCustomizer(container -> {
            container.setConcurrency(3);
            container.getContainerProperties().setIdleEventInterval(60000L); // Set the idle event interval to 60 second
        });

        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(new DeserializationErrorHandler()); // Set a custom error handler
        return factory;
    }

    // Configuring Kafka Consumer Factory
    @Bean
    public DefaultKafkaConsumerFactory<String, String> consumerFactory() {
        // Play with the different configurations such as deserialization, latency, poll and processing
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put( ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
