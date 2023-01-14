package com.tass.productservice.specification;

import com.tass.common.model.myenums.ProductStatus;
import com.tass.common.model.myenums.UserStatus;
import com.tass.productservice.entities.Brand;
import com.tass.productservice.entities.Category;
import com.tass.productservice.entities.Color;
import com.tass.productservice.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Specifications {
    public static Specification<Product> proSpec(String name, BigDecimal price, BigDecimal from, BigDecimal to, Category category, Brand brand, Color color, ProductStatus status, int page, int limit){
        return((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !(name.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+name+"%"));
            if (price != null)
                predicates.add(criteriaBuilder.greaterThan(root.get("price"),price));
            if (from != null && to != null )
                predicates.add(criteriaBuilder.between(root.get("price"),from,to));
            if (category != null)
                predicates.add(criteriaBuilder.equal(root.get("category"),category));
            if (brand != null)
                predicates.add(criteriaBuilder.equal(root.get("brand"),brand));
            if (color != null)
                predicates.add(criteriaBuilder.equal(root.get("color"),color));
            if (status != null)
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
