package com.luojia.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class MyPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String msgValues = value.toString();

        // 你要发送到某个分区时，一定要保证这个分区是存在的，不然会报错
        int partition = 0;
        // 如果消息里面有相关的关键字，则发送到指定分区
        // 如果业务需要相同的表发送到相同的分区，那么我们可以将表名当消息的key，
        // 不手动指定分区，这样就能保证同样的表数据发送到同一个分区了
        if (msgValues.contains("luojia")) {
            partition = 1;
        }

        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
