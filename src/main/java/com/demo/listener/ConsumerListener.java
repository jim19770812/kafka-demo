package com.demo.listener;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component //FIXME 暂时注释掉，只在调试kafka时开启
public class ConsumerListener {
    @KafkaListener(topics = {"test-topic"})
//    public void consumer(String message){
//        log.info("消费者: {}", message);
//    }
    public void consumer(ConsumerRecord<String, String> record){
        log.info("消费者: {}", record.toString());
    }
}