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
@Log4j2
public class ProductController extends BaseController{

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody ProductRequest request){
        log.info("Creating new product.......");
        try {
            Optional<ProductRequest> optionalProductRequest = productService.findByBarcode(request.getBarcode());
            if (optionalProductRequest.isPresent()){
                log.info("Barcode isPresent");
                return createdResponse(new BaseResponse(1000, "Barcode đã tồn tại"), HttpStatus.NOT_ACCEPTABLE);
            }
            if (request.getName() == null || request.getName().isEmpty()){
                log.info("Name null");
                return createdResponse(new BaseResponse(100, "Tên sản phẩm không được null"),HttpStatus.FORBIDDEN);
            }
            if (request.getContent() == null || request.getContent().isEmpty()){
                log.info("Content null");
                return createdResponse(new BaseResponse(101, "Content sản phẩm không được null"),HttpStatus.FORBIDDEN);
            }
            if (request.getDescription() == null || request.getDescription().isEmpty()){
                log.info("Des null");
                return createdResponse(new BaseResponse(101, "Description sản phẩm không được null"),HttpStatus.FORBIDDEN);
            }
            if (request.getImage() == null || request.getImage().isEmpty()){
                log.info("Img null");
                return createdResponse(new BaseResponse(101, "Image sản phẩm không được null"),HttpStatus.FORBIDDEN);
            }
            productService.save(request);
            log.info("Create product success :", request);
            return createdResponse(new BaseResponse(200,"ok"),HttpStatus.OK);
        }
        catch (Exception e){
            log.info("Server errors",e);
            return createdResponse(new BaseResponse(500, "Hệ thống đang quá tải, xin vui lòng thử lại sau"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
