package com.luojia.kafka.product;
import com.luojia.kafka.partitioner.MyPartitioner;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducer {

    public static void main(String[] args) {

        // 配置属性类
        Properties properties = new Properties();
        // 连接到我们的Kafka
        // 如果是集群，这里就配置多个Kafka节点
        // properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092, 127.0.0.2:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 指定对应 key 和 value 序列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 关联自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());

        // 1. 创建 Kafka 生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // 2.发送 Kafka 消息
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("first", "kafka first msg : " + i));
        }
        // 发送回调消息
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("first", "kafka first: luojia callback msg : " + i), new Callback() {
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
