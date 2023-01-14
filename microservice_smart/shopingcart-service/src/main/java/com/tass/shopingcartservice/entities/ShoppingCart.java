package com.tass.shopingcartservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tass.common.model.myenums.ShoppingCartStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long userId;

    //    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shoppingCart")
    @JsonManagedReference
    private Set<CartItem> items;

    private ShoppingCartStatus status;
}
