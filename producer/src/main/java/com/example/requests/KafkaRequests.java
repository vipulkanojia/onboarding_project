package com.example.requests;

import com.example.model.Commando;

public class KafkaRequests {

    private String type;
    private Commando commando;
    private Long id;

    public String getType() {
        return type;
    }

    public Commando getCommando() {
        return commando;
    }

    public Long getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCommando(Commando commando) {
        this.commando = commando;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
