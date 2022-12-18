package com.example.smart_shop.controller.Admin;


import com.example.smart_shop.controller.BaseController;
import com.example.smart_shop.model.request.CategoryRequest;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/api/v1")
public class CategoryController extends BaseController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(path = "/category")
    public ResponseEntity<BaseResponse> findAll()throws ApiException{
        return createdResponse(categoryService.findAll());
    }
    @PostMapping(path = "/admins/category/create")
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody CategoryRequest categoryRequest) throws ApiException{
        return createdResponse(categoryService.createCategory(categoryRequest));
    }
    @PutMapping(path = "/admins/category/update/{id}")
    public ResponseEntity<BaseResponse> update(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable Long id) throws ApiException {
        return createdResponse(categoryService.updateCategory(categoryRequest,id));
    }
    @PutMapping(path = "/admins/category/undelete/{id}")
    public ResponseEntity<BaseResponse> change(@PathVariable Long id) throws ApiException {
        return createdResponse(categoryService.unDeleteCategory(id));
    }

    @PutMapping(path = "/admins/category/delete/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id) throws ApiException {
        return createdResponse(categoryService.deleteCategory(id));
    }
}
