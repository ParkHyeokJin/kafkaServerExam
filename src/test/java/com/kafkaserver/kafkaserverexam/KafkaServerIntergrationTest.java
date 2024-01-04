package com.kafkaserver.kafkaserverexam;

import org.apache.kafka.clients.admin.TopicDescription;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class KafkaServerIntergrationTest {

    @Autowired
    KafkaService kafkaService;

    @Autowired
    KafkaAdmin kafkaAdmin;

    @Test
    void createKafkaTopic(){
        kafkaService.createTopic("test", 1, Short.parseShort("1"));
        Map<String, TopicDescription> map =  kafkaAdmin.describeTopics("test");
        for (String key : map.keySet()) {
            System.out.println("key = " + key + ", value = " + map.get(key));
        }
    }
}
