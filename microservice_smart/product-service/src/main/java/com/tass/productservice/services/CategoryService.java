package com.tass.productservice.services;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.myenums.CategoryStatus;
import com.tass.common.model.myenums.ProductStatus;
import com.tass.productservice.entities.Category;
import com.tass.productservice.entities.Product;
import com.tass.productservice.repositories.CategoryRepository;
import com.tass.productservice.repositories.ProductRepository;
import com.tass.productservice.request.CategoryRequest;
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
    @Autowired
    ProductRepository productRepository;

    public BaseResponseV2 createCategory(CategoryRequest categoryRequest) throws ApplicationException {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setThumbnail(categoryRequest.getThumbnail());
        category.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(category);
        return new BaseResponseV2(category);
    }

    public BaseResponseV2 updateCategory(CategoryRequest categoryRequest, Long id) throws ApplicationException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND, "Category not found");
        }
        Category category = categoryOptional.get();
        category.setName(categoryRequest.getName());
        category.setThumbnail(categoryRequest.getThumbnail());
        categoryRepository.save(category);
        return new BaseResponseV2<>(category);
    }

    public BaseResponseV2 deleteCategory(Long id) throws ApplicationException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND, "Category not found");
        }
        Category category = optionalCategory.get();
        category.setStatus(CategoryStatus.DEACTIVE);
        categoryRepository.save(category);
        List<Product> listProduct = productRepository.findAllByCategoryId(id);
        for (int i = 0; i < listProduct.size(); i++) {
            listProduct.get(i).setStatus(ProductStatus.DELETED);
        }
        productRepository.saveAll(listProduct);
        return new BaseResponseV2(category);
    }

    public BaseResponseV2 unDeleteCategory(Long id) throws ApplicationException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new ApplicationException(ERROR.ID_NOT_FOUND, "Category not found");
        }
        Category category = optionalCategory.get();
        category.setStatus(CategoryStatus.ACTIVE);
        categoryRepository.save(category);
        List<Product> listProduct = productRepository.findAllByCategoryId(id);
        for (int i = 0; i < listProduct.size(); i++) {
            listProduct.get(i).setStatus(ProductStatus.ACTIVE);
        }
        productRepository.saveAll(listProduct);
        return new BaseResponseV2(category);
    }

    public BaseResponseV2 findAll() throws ApplicationException {
        List<Category> category = categoryRepository.findAll();
        return new BaseResponseV2(category);
    }
}
