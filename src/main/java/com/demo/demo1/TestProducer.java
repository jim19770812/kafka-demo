package com.demo.demo1;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class TestProducer {
    public static void main(String[] args) {
         Properties props = new Properties();
         props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
         //The "all" setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.
        //“所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
         props.put(ProducerConfig.ACKS_CONFIG, "all");
         props.put(ProducerConfig.RETRIES_CONFIG, 0);//如果请求失败，生产者也会自动重试，即使设置成０ the producer can automatically retry.
         //The producer maintains buffers of unsent records for each partition.
         props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10);
         //每条消息在缓存中的最长时间，超过这个时间就自动flash
         props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
         //生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
         props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
         //The key.serializer and value.serializer instruct how to turn the key and value objects the user provides with their ProducerRecord into bytes.
         props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
         props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//         props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, TestPartitioner.class.getName());//发送的时候指定分区，不需要从这里设置
         props.put(ProducerConfig.CLIENT_ID_CONFIG, "test.producer.1");//指定客户端ID
         props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");//启用压缩

         //创建kafka的生产者类
         Producer<String, String> producer = new KafkaProducer<String, String>(props);
         //生产者的主要方法
         // close();//Close this producer.
         //   close(long timeout, TimeUnit timeUnit); //This method waits up to timeout for the producer to complete the sending of all incomplete requests.
         //  flush() ;所有缓存记录被立刻发送
//         for(int i = 0; i < 1; i++) {
//             //这里平均写入４个分区
//             producer.send(new ProducerRecord<String, String>("foo", i % 4, Integer.toString(i), Integer.toString(i)));
//         }
         ProducerRecord pr;
//         while (true){
//              pr=new ProducerRecord<String, String>("foo", 0, "名字", "张飞");
//              producer.send(pr);
//         }

//         pr=new ProducerRecord<String, String>("foo", 0, "名字", "张飞");
//         producer.send(pr);
//         producer.flush();

         int partition=getTopicFeed("foo", 10);
         for(int i=0; i<1000; i++){
              pr=new ProducerRecord<String, String>("foo", partition, "名字", "张飞");
              producer.send(pr);
              producer.flush();

         }
    }
    private static int getTopicFeed(String topic, final int partitionNum){
        if (topic==null || topic.length()==0){
            return 0;
        }
        int ret=0;
        for(int ch : topic.toCharArray()){
            ret+=(byte)ch;
        }
        return ret % partitionNum;
    }
}
