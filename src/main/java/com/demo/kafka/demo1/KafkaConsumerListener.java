package com.demo.kafka.demo1;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
@Slf4j
public class KafkaConsumerListener {
    @KafkaListener(topics = {"test"}, groupId = "g1")
    public void consumer(ConsumerRecord<String, String> record){
        log.info("消费者: {}", record.value().toString());
    }
}
