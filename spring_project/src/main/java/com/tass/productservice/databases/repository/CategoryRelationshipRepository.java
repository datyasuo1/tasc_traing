package com.tass.productservice.databases.repository;

import com.tass.productservice.databases.entities.CategoryRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRelationshipRepository extends JpaRepository<CategoryRelationship, CategoryRelationship.PK> {

    @Query(value =  "select * from category_relationship cr WHERE cr.id = ?1" , nativeQuery = true)
    List<CategoryRelationship> findAllChildrenCateByParentCateId(long parentId);

    @Query(value =  "select count(*) from category_relationship cr WHERE cr.link_id = ?1" , nativeQuery = true)
    long countParent(long id);
}
