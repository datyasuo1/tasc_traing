package com.example.smart_shop.service;

import com.example.smart_shop.databases.entities.Category;
import com.example.smart_shop.databases.myenum.CategoryStatus;
import com.example.smart_shop.databases.repository.CategoryRepository;
import com.example.smart_shop.model.request.CategoryRequest;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public BaseResponse createCategory(CategoryRequest categoryRequest)throws ApiException{
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setThumbnail(categoryRequest.getThumbnail());
        category.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(category);
        return new BaseResponse<>(200,"Success",category);
    }

    public BaseResponse updateCategory(CategoryRequest categoryRequest,Long id)throws ApiException{
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(!categoryOptional.isPresent()){
            return new BaseResponse<>(100,"Category not found");
        }
        Category category = categoryOptional.get();
        category.setName(categoryRequest.getName());
        category.setThumbnail(categoryRequest.getThumbnail());
        categoryRepository.save(category);
        return new BaseResponse<>(200,"Success",category);
    }
    public BaseResponse deleteCategory(Long id)throws ApiException{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()){
            return new BaseResponse<>(100,"Category not found");
        }
        Category category = optionalCategory.get();
        category.setStatus(CategoryStatus.DEACTIVE);
        categoryRepository.save(category);
        return new BaseResponse<>(200,"Success");
    }
    public BaseResponse unDeleteCategory(Long id)throws ApiException{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()){
            return new BaseResponse<>(100,"Category not found");
        }
        Category category = optionalCategory.get();
        category.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(category);
        return new BaseResponse<>(200,"Success");
    }

    public BaseResponse findAll()throws ApiException{
        List<Category> category = categoryRepository.findAll();
        return new BaseResponse<>(200,"Success",category);
    }
}
