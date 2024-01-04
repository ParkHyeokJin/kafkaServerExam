package com.kafkaserver.kafkaserverexam;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Primary
public class KafkaService implements Kafka{
    private final KafkaAdmin kafkaAdmin;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfig kafkaConfig;

    public KafkaService(KafkaAdmin kafkaAdmin, KafkaTemplate<String, String> kafkaTemplate, KafkaConfig kafkaConfig) {
        this.kafkaAdmin = kafkaAdmin;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfig = kafkaConfig;
    }

    @Override
    public void createTopic(String name, int numPartitions, int replicationFactor) {
        kafkaAdmin.createOrModifyTopics(TopicBuilder.name(name)
                .partitions(numPartitions)
                .replicas(replicationFactor)
                .build());

        kafkaConfig.messageListenerContainer(name).start();
    }

    @Override
    public void modifyTopic(String name, int numPartitions, int replicationFactor) {
        kafkaAdmin.createOrModifyTopics(TopicBuilder.name(name)
                .partitions(numPartitions)
                .replicas(replicationFactor)
                .build());
    }

    @Override
    public void syncSendMessage(String topicName, String message) {
        try {
            kafkaTemplate.send(topicName, message).get(10, TimeUnit.SECONDS);
            handleSuccess(topicName, message);
        }catch (Exception ex){
            handleFailure(topicName, message, ex);
        }

    }

    @Override
    public void asyncSendMessage(String topicName, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if(ex == null){
                handleSuccess(topicName, message);
            }else{
                handleFailure(topicName, message, ex);
            }
        });
    }

    public void handleSuccess(String topicName, String message){
        // some successAction
    }

    public void handleFailure(String topicName, String message, Throwable t){
        // some failureAction
    }
}
