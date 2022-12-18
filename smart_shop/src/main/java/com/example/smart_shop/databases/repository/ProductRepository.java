package com.example.smart_shop.databases.repository;

import com.example.smart_shop.databases.entities.Product;
import com.example.smart_shop.databases.myenum.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {
    Optional<Product> findByBarcode(String barcode);

    Optional<Product> findByIdAndStatus(Long id, ProductStatus productStatus);
}
