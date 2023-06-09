package com.example.request;

import com.example.model.Commando;

public class KafkaConsumerRequest {

    private String type;

    private Commando commando;

    private Long id;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Commando getCommando() {
        return commando;
    }

    public void setCommando(Commando commando) {
        this.commando = commando;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
