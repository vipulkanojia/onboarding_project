package com.example.controller;

import com.example.kafka_producer.Producer;
import com.example.model.Commando;
import com.example.requests.KafkaRequests;
import com.example.service.CommandoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    private Producer producer;

    private CommandoService commandoService;

    public ApiController(Producer producer, CommandoService commandoService){
        this.producer = producer;
        this.commandoService = commandoService;
    }

    @GetMapping(value = "/listall")
    public String listAll(){
        List<Commando> commandoList = commandoService.listAll();
        return "Returning request for all list \n" + commandoList.toString();
    }

    @GetMapping(value = "/listall/{id}")
    public String listById(@PathVariable("id") Long id){
        Commando commando = commandoService.listById(id);
        if (commando.getId() == null){
            return String.format("No commando found by %s",id);
        } else {
            return String.format("this commando found \n" + commando.toString() );
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable("id") Long id){
        commandoService.deleteById(id);
        return "Commando deleted";
    }

    @PostMapping(value = "/add")
    public String add(@RequestBody Commando commando,
                      @RequestHeader(value = "sync", defaultValue = "false") Boolean sync){
        if(sync){
            LOGGER.info("Synchronous flow to add commando in DB");
            Commando saved =  commandoService.save(commando);
            return saved.toString();
        }

        KafkaRequests kafkarequest = new KafkaRequests();
        kafkarequest.setType("Add");
        kafkarequest.setCommando(commando);
        producer.sendmessage(kafkarequest);
        return "OK";
    }

    @PutMapping(value = "/update/{id}")
    public String updateById(@PathVariable("id") Long id,
                             @RequestHeader(value = "sync", defaultValue = "false") Boolean sync,
                             @RequestBody Commando commando) {
        if(sync){
            commando = commandoService.updateById(id, commando);
            if(commando.getId() == null){
                return "commando not found";
            }
            return String.format("" +
                    "commando info updated through sync flow \n" +
                    commando);
        }
        KafkaRequests request = new KafkaRequests();
        request.setType("updateById");
        request.setCommando(commando);
        request.setId(id);
        producer.sendmessage(request);
        System.out.println(String.format("request send to kafka for updata commando info with id: %s)",id));
        return "ok";
    }

    @GetMapping(value = "/hello")
    public String fun(){
        return "hello";
    }

}
