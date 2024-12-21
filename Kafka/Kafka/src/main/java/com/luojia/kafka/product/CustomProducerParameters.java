package com.luojia.kafka.product;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 消息压缩和缓冲区大小配置
 */
public class CustomProducerParameters {

    public static void main(String[] args) {
        // 配置属性类
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 换从去大小，这里配置32M(也是默认值)
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // 批次大小，配置16K(也是默认值)
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 缓冲区消息等待时长，设置为1ms(0ms是默认值)
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // compression.type:消息压缩，默认为none，可配置值gzip、snappy、lz4、zstd
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        // 1. 创建 Kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // 2.发送 Kafka 消息
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("first", "kafka first compression msg : " + i));
        }

        // 3.关闭资源
        producer.close();
    }
}
