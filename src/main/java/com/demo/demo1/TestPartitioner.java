package com.demo.demo1;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class TestPartitioner implements Partitioner {
    private int getTopicFeed(String topic, final int partitionNum){
        if (topic==null || topic.length()==0){
            return 0;
        }
        int ret=0;
        for(int ch : topic.toCharArray()){
            ret+=(byte)ch;
        }
        return ret % partitionNum;
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int partition = this.getTopicFeed(topic, 10);
        return partition;
    }

    @Override
    public void close() {
        System.out.println("close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        System.out.println("configure");
    }
}
