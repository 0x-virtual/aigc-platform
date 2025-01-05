package org.virtual.executor.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskConsumer {

    @KafkaListener(topics = "${spring.kafka.producer.topic_task}")
    public void consumeMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            JSONObject msg = JSONObject.parseObject(record.value());
            log.info(msg.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        acknowledgment.acknowledge();
    }
}
