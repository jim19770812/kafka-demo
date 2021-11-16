package com.demo.producers;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.beans.MsgVO;

@Component //FIXME 暂时注释掉，只在调试kafka时开启
@Slf4j
@EnableScheduling //FIXME 暂时注释掉，只在调试kafka时开启
public class TestProducer {
    @Autowired
    private KafkaTemplate<?, String> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    public void send() {
        MsgVO mv=new MsgVO("吕布", "三国第一猛将", new Date());
        log.info("生产者 :{}", mv.toString());
        kafkaTemplate.send("test-topic", mv.toString());
        //send参数 1：topic,2:key,3:参数
        // 默认情况下，Kafka根据传递消息的key来进行分区的分配，即hash(key) % numPartitions,没有指定key就随机分配一个分区
    }

}