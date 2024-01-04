package com.kafkaserver.kafkaserverexam;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer implements MessageListener<String, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        LOGGER.info("received payload='{}'", data.toString());
        payload = data.toString();
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch(){
        return latch;
    }

    public String getPayload(){
        return this.payload;
    }
}
