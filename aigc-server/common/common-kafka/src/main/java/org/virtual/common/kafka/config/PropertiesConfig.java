package org.virtual.common.kafka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liujiang
 */
@Configuration
@Data
public class PropertiesConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServer;
    @Value("${spring.kafka.config.security.protocol}")
    private String securityProtocolConfig;
    @Value("${spring.kafka.config.sasl.mechanism}")
    private String saslMechanism;
    @Value("${spring.kafka.config.sasl.username}")
    private String username;
    @Value("${spring.kafka.config.sasl.password}")
    private String password;
    @Value("${spring.kafka.config.sasl.truststore_password}")
    private String truststorePassword;
    @Value("${spring.kafka.config.ssl.endpoint.identification.algorithm}")
    private String sslEndpointIdentificationAlgorithm;
    @Value("${spring.kafka.config.ssl.truststore-location}")
    private String sslTruststoreLocation;

    /**
     * 生产者配置
     */
    @Value("${spring.kafka.producer.max-block-ms}")
    private String kafkaProducerMaxBlockMs;
    @Value("${spring.kafka.producer.retries}")
    private String kafkaProducerRetries;
    @Value("${spring.kafka.producer.reconnect-backoff-ms}")
    private String kafkaProducerReconnectBackoffMs;


    /**
     * 消费组配置
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaConsumerGroupId;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private Boolean kafkaConsumerAutoCommit;
    @Value("${spring.kafka.consumer.max-poll-records}")
    private Integer kafkaConsumerMaxPollRecords;
    @Value("${spring.kafka.consumer.max-partition-fetch-bytes}")
    private Integer kafkaConsumerMaxPartitionFetchBytes;
    @Value("${spring.kafka.consumer.fetch-max-bytes}")
    private Integer kafkaConsumerFetchMaxBytes;
    @Value("${spring.kafka.consumer.session-timeout}")
    private String kafkaConsumerSessionTimeout;
    @Value("${spring.kafka.listener.batch-listener}")
    private Boolean kafkaConsumerBatchListener;
    @Value("${spring.kafka.listener.concurrency}")
    private Integer kafkaConsumerConcurrency;


}
