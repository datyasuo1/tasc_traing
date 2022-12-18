package com.tass.productservice.databases.repository;

import com.tass.productservice.databases.entities.Category;
import com.tass.productservice.model.response.SearchCategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> ,JpaSpecificationExecutor<Category> ,CategoryExtentRepository{
    Optional<Category> findById(Long id);

    List<Category> findByName(String name);

    @Query(value = "select * from category c, category_relationship cr where c.id = cr.link_id ",nativeQuery = true)
    List<Category> findAllChildren(Long id);

    @Query(value = "select * from category c, category_relationship cr where c.id = cr.id",nativeQuery = true)
    List<Category> findAllParent(Long id);



}
