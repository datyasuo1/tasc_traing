package com.tass.userservice.specification;

import com.tass.common.model.myenums.UserStatus;
import com.tass.userservice.entities.Role;
import com.tass.userservice.entities.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Specifications {
    public static Specification<User> userSpec(String username, Role role, String fullName, String email, String phone, String address, String gender, UserStatus status, int page, int limit){
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
