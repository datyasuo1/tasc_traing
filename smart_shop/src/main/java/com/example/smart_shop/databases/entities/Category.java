package com.example.smart_shop.databases.entities;

import com.example.smart_shop.databases.myenum.CategoryStatus;
import com.example.smart_shop.databases.myenum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String thumbnail;
    @Enumerated(EnumType.ORDINAL)
    private CategoryStatus status;
}
