package com.example.smart_shop.databases.repository;

import com.example.smart_shop.databases.entities.Favorite;
import com.example.smart_shop.databases.entities.FavoriteItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserId(Long userId);
}
