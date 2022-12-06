package com.tass.productservice.controllers;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.ProductRequest;
import com.tass.productservice.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/product")
//@Log4j2
public class ProductController extends BaseController{

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody ProductRequest request){
        return createdResponse(productService.createdProduct(request),HttpStatus.OK);
    }
}
