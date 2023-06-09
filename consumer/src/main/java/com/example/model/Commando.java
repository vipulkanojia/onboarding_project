package com.example.model;

import com.google.gson.Gson;
import jakarta.persistence.*;

@Entity
@Table(name = "commando")
public class Commando {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rank_;

    private String combat_zone;

    public Long getId() {
        return id;
    }

    public Commando (String rank_, String combat_zone){
        this.rank_ = rank_;
        this.combat_zone = combat_zone;
    }
    public String toJson(){
        Gson gson = new Gson();
        Commando commando = new Commando(rank_,combat_zone);
        return gson.toJson(commando);
    }

    public Commando(){

    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRank_() {
        return rank_;
    }

    public void setRank_(String rank_) {
        this.rank_ = rank_;
    }

    public String getCombat_zone() {
        return combat_zone;
    }

    public void setCombat_zone(String combat_zone) {
        this.combat_zone = combat_zone;
    }

}
