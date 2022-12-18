package com.example.smart_shop.controller.Admin;

import com.example.smart_shop.controller.BaseController;
import com.example.smart_shop.model.request.BrandRequest;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;

import com.example.smart_shop.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/api/v1")
public class BrandController extends BaseController {
    @Autowired
    BrandService brandService;

    @GetMapping(path = "/brand")
    public ResponseEntity<BaseResponse> findAll()throws ApiException {
        return createdResponse(brandService.findAll());
    }
    @PostMapping(path = "/admins/brand/create")
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody BrandRequest brandRequest) throws ApiException{
        return createdResponse(brandService.createBrand(brandRequest));
    }
    @PutMapping(path = "/admins/brand/update/{id}")
    public ResponseEntity<BaseResponse> update(@Valid @RequestBody BrandRequest brandRequest, @PathVariable Long id) throws ApiException {
        return createdResponse(brandService.updateBrand(brandRequest,id));
    }
    @PutMapping(path = "/admins/brand/undelete/{id}")
    public ResponseEntity<BaseResponse> change(@PathVariable Long id) throws ApiException {
        return createdResponse(brandService.unDeleteBrand(id));
    }

    @PutMapping(path = "/admins/brand/delete/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) throws ApiException {
        return createdResponse(brandService.deleteBrand(id));
    }
}
