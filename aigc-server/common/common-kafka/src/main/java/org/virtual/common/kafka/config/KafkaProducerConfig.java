package org.virtual.common.kafka.config;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujiang
 */
@Configuration
public class KafkaProducerConfig {



    @Autowired
    private PropertiesConfig propertiesConfig;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(15);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, propertiesConfig.getKafkaBootstrapServer());
        // Kafka 消息的序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_DOC, false);

        String securityProtocolConfig = propertiesConfig.getSecurityProtocolConfig();
        if (securityProtocolConfig.equalsIgnoreCase("PLAINTEXT")) {
            return props;
        }
        //hostname校验改成空
        props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, propertiesConfig.getSslEndpointIdentificationAlgorithm());

        props.put(ProducerConfig.RETRIES_CONFIG, propertiesConfig.getKafkaProducerRetries());
        props.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, propertiesConfig.getKafkaProducerReconnectBackoffMs());
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, propertiesConfig.getKafkaProducerMaxBlockMs());
        // ssl 认证
        KafkaSaslConfig.kafkaSaslConfig(props, propertiesConfig);
        return props;
    }
}
