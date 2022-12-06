package com.tass.productservice.databases.repository;

import com.tass.productservice.databases.entities.Category;
import com.tass.productservice.model.response.SearchCategoryResponse;

import java.util.List;

public interface CategoryExtentRepository {
    void searchCategory(Integer isRoot , String name, Integer page, Integer pageSize, SearchCategoryResponse.Data data);

    List<Category> findAll(String query);

}
