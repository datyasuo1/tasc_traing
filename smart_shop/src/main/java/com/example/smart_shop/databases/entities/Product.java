package com.example.smart_shop.databases.entities;

import com.example.smart_shop.databases.base.BaseEntity;
import com.example.smart_shop.databases.myenum.ProductStatus;
import com.example.smart_shop.databases.myenum.UserStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.org.glassfish.gmbal.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private String name;
    @Column(columnDefinition = "text")
    private String images;
    private String description;
    @Column(columnDefinition = "text")
    private String detail;
    private BigDecimal price;
    private int qty;
    private int sold;// hang da ban

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")

    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")

    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE )
//    @JoinTable(name = "product_color" ,joinColumns = @JoinColumn(name = "id")
//            ,inverseJoinColumns = @JoinColumn(name = "color_id") )
//    @JsonManagedReference
    private Color color;

    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;

}
