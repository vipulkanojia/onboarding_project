package com.example.service;

import com.example.model.Commando;
import com.example.repository.CommandoRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

import java.util.List;
import java.util.Optional;

@Service
public class CommandoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandoService.class);
    private CommandoRepo commandoRepo;

    JedisPooled jedis = new JedisPooled("localhost",6379);

    final Gson gson = new Gson();

    private CommandoService(CommandoRepo commandoRepo){
        this.commandoRepo = commandoRepo;
    }

    public List<Commando> listAll(){
        LOGGER.info("Listing all the commando");
        return commandoRepo.findAll();
    }

    public Commando listById(Long id){
        Commando commando;
        try {
            String key = "commando:" + id.toString();
            boolean idPresentInRedis = jedis.exists(key);
            if (idPresentInRedis) {
                String value = jedis.get(key);
                System.out.println(value);
                ObjectMapper mapper = new ObjectMapper();
                commando = mapper.readValue(value, Commando.class);
            } else{
                System.out.println(String.format("%s is not present in redis",key));
                Optional<Commando> commandoOptional = commandoRepo.findById(id);
                if(commandoOptional.isPresent()){
                    commando = commandoOptional.get();
                    jedis.set("commando:"+id,gson.toJson(commando));
                }else {
                    commando = new Commando();
                    LOGGER.info("Commando not found");
                }
            }
            return commando;
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public boolean deleteById(Long id){
        try {
            String key = "commando:" + id.toString();
            boolean idPresentInRedis = jedis.exists(key);
            commandoRepo.deleteById(id);
            if (idPresentInRedis) {
                jedis.del(key);
            }
            return true;
        } catch (Exception e){
            LOGGER.info(String.format("%s",e.toString()));
            return false;
        }
    }

    public Commando save(Commando commando){
        LOGGER.info("New Commando added to database");
        return commandoRepo.save(commando);
    }

    public Commando updateById(Long id, Commando newCommando){
        String key = "commando:" + id.toString();
        boolean idPresentInRedis = jedis.exists(key);
        if(idPresentInRedis){
            LOGGER.info("Deleting commando from redis");
            jedis.del(key);
        }
        Optional<Commando> commandoOptional = commandoRepo.findById(id);
        if(commandoOptional.isPresent()){
            Commando oldCommando =  commandoOptional.get();
            oldCommando.setRank_( newCommando.getRank_());
            oldCommando.setCombat_zone(newCommando.getCombat_zone());
            LOGGER.info("commando info has updated");
            return commandoRepo.save(oldCommando);
        } else {
            return new Commando();
        }
    }
}
