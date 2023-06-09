package com.example.kafkaConsumer;

import com.example.model.Commando;
import com.example.request.KafkaConsumerRequest;
import com.example.service.CommandoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class Consumer {
    private CommandoService commandoService;

    public Consumer(CommandoService commandoService) {
        this.commandoService = commandoService;
    }
    @KafkaListener(topics="onboard_topic", groupId = "parasf")
    public void consume(String jsonStr) throws JsonProcessingException {
        System.out.println(jsonStr);
        ObjectMapper mapper = new ObjectMapper();
//
        KafkaConsumerRequest obj = mapper.readValue(jsonStr, KafkaConsumerRequest.class);
        Long id = obj.getId();
//
        if(Objects.equals(obj.getType(),"Add")){
            Commando commando = obj.getCommando();
            System.out.println(commandoService.saveCommando(commando).toJson());
        }
        else if(Objects.equals(obj.getType(),"updateById")){
            Commando commando = commandoService.updateById(id,obj.getCommando());
            if(commando.getId()==null){
                System.out.println("commsndo not found");
            } else{
                System.out.println(commando.toJson());
            }
        }
        else {
            System.out.println("Invalid type");
        }
    }
}
