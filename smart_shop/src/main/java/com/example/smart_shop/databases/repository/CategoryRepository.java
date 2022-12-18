package com.example.smart_shop.databases.repository;

import com.example.smart_shop.databases.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
