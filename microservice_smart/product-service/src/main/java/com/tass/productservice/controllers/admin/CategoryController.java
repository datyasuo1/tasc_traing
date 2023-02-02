package com.tass.productservice.controllers.admin;

import com.tass.common.customanotation.RequireUserLogin;
import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.productservice.request.CategoryRequest;
import com.tass.productservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/admins/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @RequireUserLogin
    @GetMapping()
    public BaseResponseV2 findAll()throws ApplicationException {
        return categoryService.findAll();
    }
    @RequireUserLogin
    @PostMapping()
    public BaseResponseV2 create(@Valid @RequestBody CategoryRequest categoryRequest) throws ApplicationException{
        return categoryService.createCategory(categoryRequest);
    }
    @RequireUserLogin
    @PutMapping(path = "/{id}")
    public BaseResponseV2 update(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable Long id) throws ApplicationException {
        return categoryService.updateCategory(categoryRequest,id);
    }
    @RequireUserLogin
    @PutMapping(path = "/undelete/{id}")
    public BaseResponseV2 change(@PathVariable Long id) throws ApplicationException {
        return categoryService.unDeleteCategory(id);
    }

    @RequireUserLogin
    @PutMapping(path = "/delete/{id}")
    public BaseResponseV2 delete(@PathVariable Long id) throws ApplicationException {
        return categoryService.deleteCategory(id);
    }
}
