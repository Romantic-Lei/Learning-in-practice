package com.luojia.kafka.product;
import com.luojia.kafka.partitioner.MyPartitioner;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducerAcks {

    public static void main(String[] args) {

        // 配置属性类
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 指定对应 key 和 value 序列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // acks 配置
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        // 重试次数，默认的重试次数是 Max.Integer
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);

        // 1. 创建 Kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // 2.发送 Kafka 消息
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("first", "kafka first retry msg : " + i));
        }
        // 发送回调消息
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("first", 1, "hello", "kafka first: luojia callback msg : " + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("主题：" + metadata.topic() + "分区：" + metadata.partition());
                    }
                }
            });
        }

         // 3.关闭资源
        producer.close();

    }
}
