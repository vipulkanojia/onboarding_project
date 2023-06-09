package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "commando")
public class Commando {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rank_;

    private String combat_zone;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setRank_(String rank_) {
        this.rank_ = rank_;
    }

    public void setCombat_zone(String combat_zone) {
        this.combat_zone = combat_zone;
    }

    public String getRank_() {
        return rank_;
    }

    public String getCombat_zone() {
        return combat_zone;
    }

    @Override
    public String toString(){
        return "Commando{" +
                "rank_='" + rank_ + '\'' +
                ", combat_zone='" + combat_zone + '\'' +
                ", id='" + id +
                '}';
    }
}
