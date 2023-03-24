package com.dow.design.springboot.consumer.api.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeserializationErrorHandler implements CommonErrorHandler {
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container) {

        log.info("Deserialization error occurred while processing message: " + data.value());
        log.info("Error message: " + thrownException.getMessage());

        // continue deserialization by acknowledging the record
        consumer.seek(new org.apache.kafka.common.TopicPartition(data.topic(), data.partition()), data.offset());
    }
}

