package com.tass.productservice.controllers;

import com.tass.productservice.databases.entities.Category;
import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.request.CategoryRequest;
import com.tass.productservice.model.response.SearchCategoryResponse;
import com.tass.productservice.services.CategoryServices;
import com.tass.productservice.spec.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController{

    @Autowired
    CategoryServices categoryService;

    @GetMapping
    public SearchCategoryResponse search(@RequestParam(name = "is_root" , required = false) Integer isRoot , @RequestParam(required = false) String name,
                                         @RequestParam(required = false) Integer page , @RequestParam(name = "page_size" , required = false) Integer pageSize){
        return categoryService.search(isRoot, name, page, pageSize);
    }
//==========================================
    @GetMapping(path = "/getChildSelect")
    public ResponseEntity<BaseResponse> searchChildBySelect(@RequestParam(name = "id" , required = false) Long id ){
        return createdResponse(categoryService.findAllChildBySelectTable(id),HttpStatus.OK);
    }

    @GetMapping(path = "/getParentSelect")
    public ResponseEntity<BaseResponse> searchParentBySelect(@RequestParam(name = "id" , required = false) Long id ){
        return createdResponse(categoryService.findAllParentBySelectTable(id),HttpStatus.OK);
    }


    @GetMapping(path = "/getChild")
    public ResponseEntity<BaseResponse> searchChild(@RequestParam(name = "name" , required = false) String name ){
        return createdResponse(categoryService.findAllChild(name),HttpStatus.OK);
    }

    @GetMapping(path = "/getParent")
    public ResponseEntity<BaseResponse>  searchParent(@RequestParam(name = "name" , required = false) String name ){
        return createdResponse(categoryService.findAllParent(name),HttpStatus.OK);
    }

    @GetMapping(path = "/AllByView")
    public ResponseEntity<BaseResponse>  searchParentAndChildByCreateView(@RequestParam(name = "name" , required = false) String name ){
        return createdResponse(categoryService.findAllParentAndChildByCreateView(name),HttpStatus.OK);
    }
    @GetMapping(path = "/AllByQuery")
    public ResponseEntity<BaseResponse>  searchParentAndChildByQuery(@RequestParam(name = "name" , required = false) String name ){
        return createdResponse(categoryService.findAllParentAndChildByQuery(        name),HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CategoryRequest request)throws
            ApiException {
        return createdResponse(categoryService.createCategory(request), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long id)throws
            ApiException {
        return createdResponse(categoryService.deleteCategory(id), HttpStatus.OK);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody CategoryRequest request, @PathVariable Long id)throws
            ApiException {
        return createdResponse(categoryService.updateCategory(request,id), HttpStatus.OK);
    }
    @GetMapping(path = "/search")
    public Page<Category> searchCate(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "icon", required = false) String icon,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ){
        Specification<Category> specification = Specifications.cateSpec(id,name,icon,description,page,limit);
        return categoryService.searchAll(specification,page,limit);
    }
}
