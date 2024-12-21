package com.luojia.kafka.product;
import com.luojia.kafka.partitioner.MyPartitioner;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducerTransactions {

    public static void main(String[] args) {

        // 配置属性类
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        // Kafka事务消息，必须要制定事务ID，ID可以任意填写但必须全局唯一
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_id_01");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 1.初始化事务
        producer.initTransactions();
        // 2.开始事务
        producer.beginTransaction();

        // 3.发送数据
        try {
            for (int i = 0; i < 5; i++) {
                producer.send(new ProducerRecord<>("first", "kafka first transaction msg : " + i));
            }
            // 故意整一个异常，模拟事务回滚
            // int i = 10/0;
            // 4.提交事务
            producer.commitTransaction();
        } catch (Exception e) {
            // 5.回滚事务
            producer.abortTransaction();
        } finally {
            producer.close();
        }
    }
}
