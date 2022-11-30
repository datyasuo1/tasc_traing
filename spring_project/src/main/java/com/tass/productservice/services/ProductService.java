package com.tass.productservice.services;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.ProductRequest;
import com.tass.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public BaseResponse createdProduct(ProductRequest request){

        return new BaseResponse();
    }
    public Optional<ProductRequest> findByBarcode(String barcode){
        return productRepository.findByBarcode(barcode);
    }
    public ProductRequest save(ProductRequest productRequest){
        return productRepository.save(productRequest);
    }
}
