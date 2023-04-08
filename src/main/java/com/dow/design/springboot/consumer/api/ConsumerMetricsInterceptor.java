package com.dow.design.springboot.consumer.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The ConsumerInterceptor interface allows developers to intercept and modify records consumed by a Kafka consumer.
 * <p>
 * Using a ConsumerInterceptor, custom logic can be added to perform actions such as filtering or transforming the records
 * before they are passed to the application, logging additional information about the records, or modifying the records themselves.
 * <p>
 * This can be particularly useful in situations where pre-processing is needed on the records before they are consumed by the
 * application. For example, metadata can be added to the records to track their origin or route them to different Kafka topics
 * based on certain criteria.
 * <p>
 * By implementing a ConsumerInterceptor, this custom logic can be added to the Kafka consumer without modifying application
 * code directly.
 */
@Slf4j
@Component
public class ConsumerMetricsInterceptor implements ConsumerInterceptor<String, String> {

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> consumerRecords) {
        if (consumerRecords.isEmpty()) {
            return consumerRecords;
        } else {
            for (ConsumerRecord<String, String> records : consumerRecords) {
                Headers headers = records.headers();
                for (Header header : headers) {
                    if ("traceId".equals(header.key())) {
                        log.info("traceId coming from our springboot-producer-api {} with a key of {}",
                                new String(header.value()), records.key());
                    }
                }
            }
        }
        return consumerRecords;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
        // Leave this method body empty
    }

    @Override
    public void close() {
        // Leave this method body empty
    }

    @Override
    public void configure(Map<String, ?> map) {
        // Leave this method body empty
    }
}
