package com.example.kafka_producer;

import com.example.model.Commando;
import com.example.requests.KafkaRequests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private KafkaTemplate<String,String> kafkaTemplate;

    public Producer(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendmessage(KafkaRequests data){
        LOGGER.info(String.format("Sending message to kafka -----> %s",data.toString()));
        ObjectMapper Obj = new ObjectMapper();

        try {
            String jsonObj = Obj.writeValueAsString(data);
            System.out.print(jsonObj);
            kafkaTemplate.send("onboard_topic",jsonObj);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
