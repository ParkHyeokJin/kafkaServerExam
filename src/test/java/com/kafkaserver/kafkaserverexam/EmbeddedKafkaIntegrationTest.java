package com.kafkaserver.kafkaserverexam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class EmbeddedKafkaIntegrationTest {
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Value("${test.topic}")
    private String topic;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {
        String data = "Sending with our own simple KafkaProducer";

        kafkaProducer.send(topic, data);

        boolean messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);

        Assertions.assertThat(messageConsumed).isTrue();
        Assertions.assertThat(kafkaConsumer.getPayload()).contains(data);
    }
}
