package com.demo.demo1;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class TestConsumer {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "jbcfront:9092");
        System.out.println("this is the group part test 1");
        //消费者的组id
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "GroupA");//设置消费者分组，确保一个集群里有一台消费者可以接收到消息

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");//自动提交开启后每隔AUTO_COMMIT_INTERVAL_MS_CONFIG毫秒提交一次
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");//1秒提供一次offset

        //从poll(拉)的回话处理时长
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //poll的数量限制
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");//每次从最大消费位置开始消费
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "test.consumer.1");//指定客户端ID
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //订阅主题列表topic
        consumer.subscribe(Arrays.asList("dev1"));

        int temp=0;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis((long)1000));
            for (ConsumerRecord<String, String> record : records){
                //　正常这里应该使用线程池处理，不应该在这里处理
                System.out.printf("[收到消息] partition= %d offset = %d, key = %s, value = %s", record.partition(), record.offset(), record.key(), record.value()+"\n");
                if (record.value().contains("吕布") && temp==0){
                    temp++;
                    continue;
                }
                consumer.commitSync();
            }
        }
    }
}
