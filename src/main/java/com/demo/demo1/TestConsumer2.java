package com.demo.demo1;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TestConsumer2 extends TestConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        System.out.println("this is the group part test 1");
        //消费者的组id
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "GroupA");//设置消费者分组，确保一个集群里有一台消费者可以接收到消息

        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");

        //从poll(拉)的回话处理时长
        props.put("session.timeout.ms", "30000");
        //poll的数量限制
        //props.put("max.poll.records", "100");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //订阅主题列表topic
        consumer.subscribe(Arrays.asList("foo"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100000);
            for (ConsumerRecord<String, String> record : records)
                //　正常这里应该使用线程池处理，不应该在这里处理
                System.out.printf("[收到消息] offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value()+"\n");

        }
    }

}
