package com.kafkaserver.kafkaserverexam;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@SpringBootApplication
public class KafkaServerExamApplication {

    private final KafkaConfig kafkaConfig;

    public KafkaServerExamApplication(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaServerExamApplication.class, args);
    }

    @PostConstruct
    void startDefaultListener(){
        ConcurrentMessageListenerContainer<String, String> container = kafkaConfig.messageListenerContainer("hello.kafka");
        container.start();
    }
}
