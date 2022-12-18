package com.example.smart_shop.databases.repository;

import com.example.smart_shop.databases.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color,Long> {
    Optional<Color> findByName(String name);
}
