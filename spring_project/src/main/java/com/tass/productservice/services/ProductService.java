package com.tass.productservice.services;

import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.ProductRequest;
import com.tass.productservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@Log4j2
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public BaseResponse createdProduct(ProductRequest request){

        try {
            Optional<ProductRequest> optionalProductRequest = productRepository.findByBarcode(request.getBarcode());
            if (optionalProductRequest.isPresent()){
//                log.info("Barcode isPresent");
                return new BaseResponse(1000, "Barcode đã tồn tại");
            }
            if (request.getName() == null || request.getName().isEmpty()){
//                log.info("Name null");
                return new BaseResponse(100, "Tên sản phẩm không được null");
            }
            if (request.getContent() == null || request.getContent().isEmpty()){
//                log.info("Content null");
                return new BaseResponse(101, "Content sản phẩm không được null");
            }
            if (request.getDescription() == null || request.getDescription().isEmpty()){
//                log.info("Des null");
                return new BaseResponse(101, "Description sản phẩm không được null");
            }
            if (request.getImage() == null || request.getImage().isEmpty()){
//                log.info("Img null");
                return new BaseResponse(101, "Image sản phẩm không được null");
            }
            productRepository.save(request);
//            log.info("Create product success :", request);
            return new BaseResponse(200,"ok");
        }
        catch (Exception e){
//            log.info("Server errors",e);
            return new BaseResponse(500, "Hệ thống đang quá tải, xin vui lòng thử lại sau");
        }
    }
}
