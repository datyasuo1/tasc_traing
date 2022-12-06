package com.tass.productservice.spec;

import com.tass.productservice.databases.entities.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class Specifications {
    public static Specification<Category>cateSpec(Long id,String name,String icon,String description,int page,int limit){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (id != null )
                predicates.add(criteriaBuilder.equal(root.get("id"), id ));
            if (name != null &&  !(name.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+name+"%"));
            if (icon != null &&  !(icon.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("icon"),"%"+icon+"%"));
            if (description != null &&  !(description.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("description"),"%"+description+"%"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
