package com.dow.design.springboot.consumer.api;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class Consumer {

    @Autowired
    ConsumerMetricsInterceptor consumerMetricsInterceptor;

    @KafkaListener(topics = "input-topic")
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("Partition: {}, Offset: {}, Event: {}", consumerRecord.partition(), consumerRecord.offset(),
                consumerRecord.value());
    }
}
