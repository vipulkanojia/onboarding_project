package com.example.service;

import com.example.model.Commando;
import com.example.repository.CommandoRepo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

import java.util.Optional;

@Service
public class CommandoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandoService.class);

    private CommandoRepo commandoRepo;

    public CommandoService(CommandoRepo commandoRepo){
        this.commandoRepo = commandoRepo;
    }

    JedisPooled jedis = new JedisPooled("localhost",6379);

    final Gson gson = new Gson();

    public Commando saveCommando(Commando commando){
        return commandoRepo.save(commando);
    }

    public Commando updateById(Long id, Commando newCommando){
        String key = "commando:"+id.toString();
        boolean isPresentInRedis = jedis.exists(key);
        if(isPresentInRedis){
            LOGGER.info("Deleting before update in redis");
            jedis.del(key);
        }
        Optional<Commando> commandoOptional = commandoRepo.findById(id);
        if(commandoOptional.isPresent()){
            Commando oldCommando = commandoOptional.get();
            oldCommando.setCombat_zone(newCommando.getCombat_zone());
            oldCommando.setRank_(newCommando.getRank_());
            LOGGER.info("Update the entity");
            return commandoRepo.save(oldCommando);
        } else {
            return new Commando();
        }
    }

}
