package com.tass.orderservice.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tass.common.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseEntity {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Order order;

    private long product;

    private Integer quantity;
    private BigDecimal unitPrice;
}
