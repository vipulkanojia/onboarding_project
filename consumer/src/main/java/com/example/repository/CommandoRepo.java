package com.example.repository;

import com.example.model.Commando;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandoRepo extends JpaRepository<Commando,Long> {
}
