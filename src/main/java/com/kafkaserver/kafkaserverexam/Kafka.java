package com.kafkaserver.kafkaserverexam;

public interface Kafka {
    void createTopic(String name, int numPartitions, int replicationFactor);

    void modifyTopic(String name, int numPartitions, int replicationFactor);

    void syncSendMessage(String topicName, String message);
    void asyncSendMessage(String topicName, String message);
}
