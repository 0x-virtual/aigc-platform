package org.virtual.common.kafka;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;


@Log4j2
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Value("${spring.kafka.producer.task_topic:task_topic}")
    private String kafkaTaskTopic;

    public String sendTask(JSONObject message) {
        try {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTaskTopic, message.toString());
            SendResult<String, String> result = future.get();
            return result.getProducerRecord().toString();
        } catch (Exception e) {
        }
        return "send message failed.";
    }

}
