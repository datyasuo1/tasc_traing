package com.tass.productservice.repository;

import com.tass.productservice.model.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductRequest, Integer> {
    Optional<ProductRequest> findByBarcode(String barcode);
}
