package com.example.smart_shop.specification;

import com.example.smart_shop.databases.entities.*;
import com.example.smart_shop.databases.myenum.ProductStatus;
import com.example.smart_shop.databases.myenum.UserStatus;
import org.springframework.data.jpa.domain.Specification;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

public class Specifications {
    public static Specification<Product> proSpec(String name, BigDecimal price,BigDecimal from,BigDecimal to, Category category, Brand brand, Color color, ProductStatus status,int page, int limit){
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
    public static Specification<User> userSpec(String username, Role role, String fullName, String email, String phone, String address,String gender, UserStatus status, int page, int limit){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (username != null &&  !(username.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("username"),"%"+username+"%"));
            if (role != null)
                predicates.add(criteriaBuilder.equal(root.get("role"),role));
            if (fullName != null &&  !(fullName.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("fullName"),"%"+fullName+"%"));
            if (email != null &&  !(email.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("email"), "%" +email +"%"));
            if (phone != null &&  !(phone.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("phone"),"%" +phone +"%"));
            if (address != null &&  !(address.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("address"),"%"+address+"%"));
            if (gender != null && !(gender.isEmpty()))
                predicates.add(criteriaBuilder.equal(root.get("gender"),gender));
            if (status != null)
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
