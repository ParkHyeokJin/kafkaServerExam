package com.kafkaserver.kafkaserverexam;

import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.bind.annotation.*;

@RestController("/kafka")
public class KafkaController {

    private final KafkaService kafkaService;

    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping("/create")
    public void createTopic(@RequestParam String name,
                            @RequestParam(defaultValue = "1") int numPartitions,
                            @RequestParam(defaultValue = "1") int replicationFactor){

        kafkaService.createTopic(name, numPartitions, replicationFactor);
    }

    @GetMapping("/modify")
    public void modifyTopic(@RequestParam String name,
                            @RequestParam(defaultValue = "1") int numPartitions,
                            @RequestParam(defaultValue = "1") short replicationFactor){
        kafkaService.createTopic(name, numPartitions, replicationFactor);
    }

    @GetMapping("/syncSend")
    public void syncSend(@RequestParam String topicName, @RequestParam String message){
        kafkaService.syncSendMessage(topicName, message);
    }

    @GetMapping("/asyncSend")
    public void asyncSend(@RequestParam String topicName, @RequestParam String message){
        kafkaService.asyncSendMessage(topicName, message);
    }
}
