package com.example.smart_shop.databases.repository;

import com.example.smart_shop.databases.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand,Long> {
}
