package com.tass.orderservice.spec.User;


import com.tass.common.model.myenums.OrderStatus;
import com.tass.orderservice.entities.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Specifications {
    public static Specification<Order> OrderSpec( OrderStatus status, Long userId , int page, int limit){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null)
                predicates.add(criteriaBuilder.equal(root.get("status"),status));
            if (userId != null )
                predicates.add(criteriaBuilder.equal(root.get("userId"),userId));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
